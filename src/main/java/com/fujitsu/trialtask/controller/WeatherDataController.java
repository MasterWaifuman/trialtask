package com.fujitsu.trialtask.controller;

import java.net.URI;
import java.net.URL;
import java.util.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fujitsu.trialtask.FeeCalc;
import com.fujitsu.trialtask.model.Observations;
import com.fujitsu.trialtask.model.Order;
import com.fujitsu.trialtask.model.WeatherData;
import com.fujitsu.trialtask.repository.WeatherDataRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Description;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class WeatherDataController {

    @Autowired
    WeatherDataRepository weatherDataRepository;

    /**
     * Order page
     *
     * @return Response
     */
    @GetMapping("/order")
    //Order page
    public ResponseEntity<Order> order() {
        return new ResponseEntity<>(new Order(), HttpStatus.OK);
    }

    /**
     * Request to calculate delivery fee based on order data entered
     *
     * @param order order data from form
     * @return Response with order with calculated delivery fee
     */
    @PostMapping(value = "/order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        try {
            WeatherData weatherData = weatherDataRepository.findByName(order.getCity()).get(weatherDataRepository.findByName(order.getCity()).size() - 1);
            order.setFee(FeeCalc.calculate(weatherData, order.getVehicle()));
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Request to get weather data
     *
     * @param name (optional) name of the station
     * @return Response with requested weather data
     */
    @GetMapping(value = "/weather", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<WeatherData>> getAllWeather (@RequestParam(required = false) String name) {
            List<WeatherData> weatherData = new ArrayList<>();

            if (name == null)
                weatherData.addAll(weatherDataRepository.findAll());
            else
                weatherData.addAll(weatherDataRepository.findByName(name));

            return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

    /**
     * Gets weather data from the link and populates DB
     *
     * @return HTTP status
     */
    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "${cron.expression}")
    @PostMapping(value = "/weather", consumes = { MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HttpStatus> createWeatherData() {
        try {
            URL url = URI.create("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php").toURL();
            XmlMapper xmlMapper = new XmlMapper();
            Observations observations = xmlMapper.readValue(url, Observations.class);
            observations.getWeatherData().forEach(weather -> {
                /*if (weather.getName().equals("Tallinn-Harku") || weather.getName().equals("Tartu-Tõravere") || weather.getName().equals("Pärnu")) {
                    weather.setTimestamp(observations.getTimestamp());
                    weatherDataRepository.save(weather);
                }*/
                //i am not really sure, but it seems you want input to be tallinn/tartu/parnu, so here we go
                switch (weather.getName()) {
                    case "Tallinn-Harku" -> {
                        weather.setName("Tallinn");
                        weather.setTimestamp(observations.getTimestamp());
                        weatherDataRepository.save(weather);
                    }
                    case "Tartu-Tõravere" -> {
                        weather.setName("Tartu");
                        weather.setTimestamp(observations.getTimestamp());
                        weatherDataRepository.save(weather);
                    }
                    case "Pärnu" -> {
                        weather.setTimestamp(observations.getTimestamp());
                        weatherDataRepository.save(weather);
                    }
                }
            });
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Not used by frontend, but can be used to clear DB
     *
     * @return HTTP status
     */
    @DeleteMapping("/weather")
    public ResponseEntity<HttpStatus> deleteAllWeatherData() {
        try {
            weatherDataRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
