package me.chrissembiring.tap2fly;

public class FlightManifest
{
    private int id;
    private String name;
    private String seatno;
    private String flightno;
    private int boardingtime;
    private int etd;
    private int eta;

    public FlightManifest()
    {

    }

    public FlightManifest(String name, String seatno, String flightno, int boardingtime, int etd, int eta)
    {
        this.name = name;
        this.seatno = seatno;
        this.flightno = flightno;
        this.boardingtime = boardingtime;
        this.etd = etd;
        this.eta = eta;
    }

    public FlightManifest(int id, String name, String seatno, String flightno, int boardingtime, int etd, int eta)
    {
        this.id = id;
        this.name = name;
        this.seatno = seatno;
        this.flightno = flightno;
        this.boardingtime = boardingtime;
        this.etd = etd;
        this.eta = eta;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSeatno()
    {
        return seatno;
    }

    public void setSeatno(String seatno)
    {
        this.seatno = seatno;
    }

    public String getFlightno()
    {
        return flightno;
    }

    public void setFlightno(String flightno)
    {
        this.flightno = flightno;
    }

    public int getBoardingtime()
    {
        return boardingtime;
    }

    public void setBoardingtime(int boardingtime)
    {
        this.boardingtime = boardingtime;
    }

    public int getEtd()
    {
        return etd;
    }

    public void setEtd(int etd)
    {
        this.etd = etd;
    }

    public int getEta()
    {
        return eta;
    }

    public void setEta(int eta)
    {
        this.eta = eta;
    }

}