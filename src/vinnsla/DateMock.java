package vinnsla;

public class DateMock implements Date {

    private int availability;

    public DateMock(int availability) {
        this.availability = availability;
    }

    @Override
    public int getAvailability() {
        return availability;
    }

    @Override
    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
