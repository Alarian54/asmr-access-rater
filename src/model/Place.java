package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Place {

    private String name;
    private List<Review> reviews;

    public Place(String name) {
        this.name = name;
        this.reviews = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> printArray() {
        List<String> printArray = new ArrayList<>();
        printArray.add(name);
        for (Review review: reviews){
            printArray.add(" - " + review.string());
        }
        if (reviews.size()==0) {
            printArray.add(" - No reviews yet");
        }
        return printArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(name, place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
