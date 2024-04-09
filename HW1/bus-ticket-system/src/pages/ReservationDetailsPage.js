import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

function ReservationDetailsPage() {
  const [reservationDetails, setReservationDetails] = useState(null);
  const [currencies, setCurrencies] = useState([]);
  const [selectedCurrency, setSelectedCurrency] = useState('');
  const [reservationDetailsNotFound, setReservationDetailsNotFound] = useState(false);
  const location = useLocation();
  const reservationToken = location.state?.reservationToken;

  useEffect(() => {
    if (reservationToken) {
      // Make API call to retrieve reservation details using the token
      axios.get(`http://localhost:8080/api/reservation/${reservationToken}`)
        .then(response => {
          setReservationDetails(response.data);
          console.log(response.data);
        })
        .catch(error => {
          console.error('Error fetching reservation details:', error);
          if (error.response && error.response.status === 404) {
            // Reservation not found
            setReservationDetailsNotFound(true);
          }
        });
    }

    // Fetch currencies
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
  }, [reservationToken]);

  const handleCurrencyChange = (event) => {
    setSelectedCurrency(event.target.value);
  };

  return (
    <div>
      <h1>Reservation Details</h1>
      {reservationDetailsNotFound ? (
        <p>Reservation not found.</p>
      ) : reservationDetails ? (
        <div>
          <p>Reservation Token: {reservationDetails.reservationToken}</p>
          <p>Passenger Name: {reservationDetails.passengerName}</p>
          <p>Passenger Surname: {reservationDetails.passengerSurname}</p>
          <h2>Bus Details</h2>
          <p>Origin City: {reservationDetails.bus.originCity.name}</p>
          <p>Destination City: {reservationDetails.bus.destinationCity.name}</p>
          <p>Departure Date and Time: {reservationDetails.bus.departureDate} {reservationDetails.bus.departureTime}</p>
          <p>Arrival Date and Time: {reservationDetails.bus.arrivalDate} {reservationDetails.bus.arrivalTime}</p>
          <p>Available seats {reservationDetails.bus.availableSeats} out of {reservationDetails.bus.totalSeats}</p>
          <p>Price: {(currencies[selectedCurrency] * reservationDetails.bus.priceInEuro).toFixed(2)} {selectedCurrency}</p>
          <div>
            Select Currency:
            <select value={selectedCurrency} onChange={handleCurrencyChange}>
              {Object.keys(currencies).map(currency => (
                <option key={currency} value={currency}>{currency}</option>
              ))}
            </select>
          </div>
        </div>
      ) : (
        <p>Loading reservation details...</p>
      )}
    </div>
  );
}

export default ReservationDetailsPage;
