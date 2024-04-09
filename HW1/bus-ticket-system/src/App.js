// src/App.js

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import SearchBusesPage from './pages/SearchBusesPage';
import BookBusPage from './pages/BookBusPage';
import ReservationDetailsPage from "./pages/ReservationDetailsPage";

// Import other page components here

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" exact element={<HomePage/>} />
        <Route path="/search-buses" exact element={<SearchBusesPage/>} />
        <Route path="/book-bus" exact element={<BookBusPage/>} />
        <Route path="/reservation-details" exact element={<ReservationDetailsPage/>} />
      </Routes>
    </Router>
  );
}

export default App;
