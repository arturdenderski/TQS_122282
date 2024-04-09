import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function HomePage() {
  const [cities, setCities] = useState([]);
  const [originCity, setOriginCity] = useState('');
  const [destinationCity, setDestinationCity] = useState('');
  const [departureDate, setDepartureDate] = useState('');
  const [reservationToken, setReservationToken] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('http://localhost:8080/api/cities')
      .then(response => {
        setCities(response.data);
      })
      .catch(error => {
        console.error('Error fetching cities:', error);
      });
  }, []);

  const handleSearch = async (event) => {
    event.preventDefault();
    try {
      navigate('/search-buses', {
        state: {
          originCity,
          destinationCity,
          departureDate
        }
      });
    } catch (error) {
      console.error('Error navigating to search buses page:', error);
    }
  };

  const handleTokenSubmit = () => {
    navigate(`/reservation-details`, {
      state: {
        reservationToken: reservationToken
      }
    });
  };

  return (
    <div>
      <h1>Bus Ticket Booking System</h1>
      <form onSubmit={handleSearch}>
        <label>
          Origin City:
          <select value={originCity} onChange={(e) => setOriginCity(e.target.value)} required>
            <option value="">Select Origin City</option>
            {cities.map(city => (
              <option key={city.id} value={city.name}>{city.name}</option>
            ))}
          </select>
        </label>
        <label>
          Destination City:
          <select value={destinationCity} onChange={(e) => setDestinationCity(e.target.value)} required>
            <option value="">Select Destination City</option>
            {cities.map(city => (
              <option key={city.id} value={city.name}>{city.name}</option>
            ))}
          </select>
        </label>
        <label>
          Departure Date:
          <input
            type="date"
            value={departureDate}
            onChange={(e) => setDepartureDate(e.target.value)}
            required
          />
        </label>
        <button type="submit">Search</button>
      </form>

      <h2>Reservation Details</h2>
      <label>
        Enter Reservation Token:
        <input
          type="text"
          value={reservationToken}
          onChange={(e) => setReservationToken(e.target.value)}
          required
        />
      </label>
      <button onClick={handleTokenSubmit}>Submit</button>
    </div>
  );
}

export default HomePage;
