package Domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private String seeAll;
    private String models;
    private int kilometrers;
    private double price;
    private boolean rent;


    public Car(int kilometros){
        this.kilometrers = kilometros;
    }
    public Car(boolean alquilar){
        this.rent = alquilar;
    }

}
