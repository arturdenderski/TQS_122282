import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

function SearchBusesPage() {
  const [connections, setConnections] = useState([]);
  const [currencies, setCurrencies] = useState([]);
  const [selectedCurrency, setSelectedCurrency] = useState('');
  const { state } = useLocation();
  const { originCity, destinationCity, departureDate } = state;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchConnections = async () => {
      const url = `http://localhost:8080/api/connections?originCity=${encodeURIComponent(originCity)}&destinationCity=${encodeURIComponent(destinationCity)}&departureDate=${departureDate}`;
      try {
        const response = await axios.get(url);
        setConnections(response.data);
      } catch (error) {
        console.error('Error fetching connections:', error);
      }
    };

    fetchConnections();
  }, [originCity, destinationCity, departureDate]);

  useEffect(() => {
    const fetchCurrencies = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/currencies');
        console.log(response);
        setCurrencies(response.data);
        setSelectedCurrency("EUR");
      } catch (error) {
        console.error('Error fetching currencies:', error);
      }
    };

    fetchCurrencies();
  }, []);

  const handleReserve = (connectionId) => {
    navigate('/book-bus', {
      state: {
        connectionId,
        originCity,
        destinationCity,
        departureDate,
        selectedCurrency
      }
    });
  };

  const handleCurrencyChange = (event) => {
    setSelectedCurrency(event.target.value);
  };

  return (
    <div>
      <h1>Bus Connections</h1>
      <h2>Origin City: {originCity}</h2>
      <h2>Destination City: {destinationCity}</h2>
      <h2>Departure Date: {departureDate}</h2>
      <h2>Connections:</h2>
      <div>
        Select Currency:
        <select value={selectedCurrency} onChange={handleCurrencyChange}>
          {Object.keys(currencies).map(currency => (
            <option key={currency} value={currency}>{currency}</option>
          ))}
        </select>
      </div>
      <ul>
        {connections.map(connection => (
          <li key={connection.id}>
            From: {connection.originCity.name} - To: {connection.destinationCity.name}<br />
            Departure Time: {connection.departureTime} - Arrival Time: {connection.arrivalTime}<br />
            Price: {(currencies[selectedCurrency] * connection.priceInEuro).toFixed(2)} {selectedCurrency}<br />
            Available Seats: {connection.availableSeats} out of {connection.totalSeats}<br />
            <button onClick={() => handleReserve(connection.id)}>Reserve</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default SearchBusesPage;
