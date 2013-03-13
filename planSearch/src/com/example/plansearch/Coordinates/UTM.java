package com.example.plansearch.Coordinates;

public class UTM
{
	public UTM(String str)
	{
		String[] parts = str.split(" ");
		LongtitudeBand = Integer.parseInt(parts[0]);
		LatitudeBand = parts[1].charAt(0);
		Easting = Double.parseDouble(parts[2]);
		Northing = Double.parseDouble(parts[3]);
	}
	@Override
	public String toString()
	{
		return String.format("%s %s %f %f", LongtitudeBand, LatitudeBand, Easting, Northing);
	}
	public UTM(int longtitudeBand, char latitudeBand, double easting, double northing)
	{
		LatitudeBand = latitudeBand;
		LongtitudeBand = longtitudeBand;
		Easting = easting;
		Northing = northing;
	}
	//32 V 569538 7033855
	public final int LongtitudeBand;
	public final char LatitudeBand;
	public final double Easting;
	public final double Northing;
	
	public UTM AddDistance(double easting, double northing)
	{
		return new UTM(this.LongtitudeBand, this.LatitudeBand,
				this.Easting + easting, this.Northing + northing);
	}
}