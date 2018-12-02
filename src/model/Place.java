package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Place {
    private String name;
    private String type;
    private int ticks;
    private int maxTicks;
    private List<Review> reviews;

    public Place(String name, String type, int ticks, int maxTicks) {
        this.name = name;
        this.type = type;
        this.ticks = ticks;
        this.maxTicks = maxTicks;
        this.reviews = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getTicks() {
        return ticks;
    }

    public int getMaxTicks() {
        return maxTicks;
    }

    public void setTicks(int i) {
        ticks = i;
    }

    public void setMaxTicks(int i) {
        maxTicks = i;
    }

    public void addTick() {
        ticks += 1;
    }

    public void addMaxTick() {
        maxTicks += 1;
    }

    public void addReview(Review review) {
        reviews.add(review);
        ticks = 0;
        for (Review r : reviews) {
            ticks+=review.getStars();
        }
        ticks = ticks/reviews.size();
    }

    public List<String> printArray() {
        List<String> printArray = new ArrayList<>();
        printArray.add(name);
        if (maxTicks==0) {
            printArray.add(" - Not scored yet");
        } else {
            printArray.add(" - Score: " + ticks + "/" + maxTicks);
        }
//        for (Review review: reviews){
//            printArray.add(" - " + review.string());
//        }
//        if (reviews.size()==0) {
//            printArray.add(" - No reviews yet");
//        }
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
