import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar.js';

function WeatherList() {
    const [loading, setLoading] = useState(true)
    const [weatherlist, setWeather] = useState({weatherlist: []});

    useEffect(() => {
        const fetchWeather = async() => {
            setLoading(true);
            try {
                await fetch('/api/weather').then(response => response.json()).then(data => setWeather(data));
            } catch(err) {
                console.log(err);
            }
            setLoading(false);
        };
        fetchWeather();
    }, []);

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <h3>Weather Data</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="16%">Station</th>
                        <th width="16%">WMO Code</th>
                        <th width="16%">Air Temp</th>
                        <th width="16%">Wind Speed</th>
                        <th width="16%">Phenomenon</th>
                        <th width="16%">Timestamp</th>
                    </tr>
                    </thead>
                    {!loading && (
                    <tbody>
                    {weatherlist.map((weather) => (
                        <tr key={weather.id}>
                            <td style={{whiteSpace: 'nowrap'}}>{weather.name}</td>
                            <td>{weather.wmocode}</td>
                            <td>{weather.airtemperature}</td>
                            <td>{weather.windspeed}</td>
                            <td>{weather.phenomenon}</td>
                            <td>{weather.timestamp}</td>
                        </tr>))}
                    </tbody>
                    )}
                </Table>
            </Container>
        </div>
    );
}
export default WeatherList;