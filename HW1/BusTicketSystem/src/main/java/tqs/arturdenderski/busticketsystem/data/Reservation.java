package tqs.arturdenderski.busticketsystem.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    private String reservationToken;

    @ManyToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "id")
    private Bus bus;

    @NotNull
    @Size(max = 128)
    private String passengerName;

    @NotNull
    @Size(max = 128)
    private String passengerSurname;

    public Reservation() {
        this.reservationToken = generateToken();
    }

    public Reservation(@NonNull Bus bus, @NonNull String passengerName, @NonNull String passengerSurname) {
        this.reservationToken = generateToken();
        this.bus = bus;
        this.passengerName = passengerName;
        this.passengerSurname = passengerSurname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationToken() {
        return reservationToken;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerSurname() {
        return passengerSurname;
    }

    public void setPassengerSurname(String passengerSurname) {
        this.passengerSurname = passengerSurname;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void setReservationToken(String token) {
        this.reservationToken = token;
    }
}
