package com.fujitsu.trialtask.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fujitsu.trialtask.model.WeatherData;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByName(String name);
}
