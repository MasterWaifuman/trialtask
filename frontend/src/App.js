import './App.css';
import Home from './Home';
import { HashRouter as Router, Route, Routes } from 'react-router-dom';
import WeatherList from './components/WeatherList';
import Order from './components/Order';

function App() {
    return (
        <Router>
          <Routes>
            <Route index element={<Home />}/>
            <Route path='api' element={<Home />}/>
            <Route path='api/order' element={<Order />}/>
            <Route path='api/weather' element={<WeatherList />}/>
          </Routes>
        </Router>
    )
}

export default App;
