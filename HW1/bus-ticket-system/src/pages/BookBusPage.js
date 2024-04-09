import React, { useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate} from 'react-router-dom';

function BookBusPage() {
  const { state } = useLocation();
  const { connectionId, originCity, destinationCity, departureDate } = state;
  const [passengerName, setPassengerName] = useState('');
  const [passengerSurname, setPassengerSurname] = useState('');
  const [reservationToken, setReservationToken] = useState('');
  const [error, setError] = useState('');

  const handleReservation = async () => {
    try {
      const url = `http://localhost:8080/api/reserve?busId=${connectionId}&passengerName=${passengerName}&passengerSurname=${passengerSurname}`;
      const response = await axios.post(url);
      setReservationToken(response.data); // Assuming the response contains the reservation token
      setError('');
    } catch (error) {
      if (error.response.status === 400) {
        setError('Failed to reserve. No available seats or bad request!');
      } else {
        setError('Failed to reserve. Please try again.');
      }
    }
  };
  

  return (
    <div>
      {!reservationToken && !error && (
        <>
          <h1>Book Bus</h1>
          <p>Origin City: {originCity}</p>
          <p>Destination City: {destinationCity}</p>
          <p>Departure Date: {departureDate}</p>
          <label>
            Name:
            <input type="text" value={passengerName} onChange={(e) => setPassengerName(e.target.value)} />
          </label>
          <label>
            Surname:
            <input type="text" value={passengerSurname} onChange={(e) => setPassengerSurname(e.target.value)} />
          </label>
          <button onClick={handleReservation}>Reserve</button>
        </>
      )}
  
      {error && <p>{error}</p>}
      {reservationToken && <p>{reservationToken}</p>}
    </div>
  );
  
}

export default BookBusPage;
