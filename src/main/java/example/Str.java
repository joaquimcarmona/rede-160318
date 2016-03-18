package example;


import example.Red;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;


@Entity
public class Str {
  @Parent Key<Red> network;
  @Id public Long Id;
  
  @Index private int stretchNumber;
  private String stationA;
  private String stationB;
  private float distance;
  private int nextABFirst;		// next stretch in the AB direction
  private int nextABSecond;	// for bifurcations
  private int nextABThird; 	// in case there is a third branch out of B
  private int nextBAFirst;		// next stretch in the BA direction
  private int nextBASecond;	// for bifurcations
  private int nextBAThird;		// in case there is a third branch out of A
  
  public Str() { 
  	network = Key.create(Red.class,"RedeMP");
  }
  
  public void setStretchNumber (int number) {
    this.stretchNumber = number;
  }
  
  public int getStretchNumber (Str s) {
    return s.stretchNumber;
  }
  
  public void setStationA (String s) {
    this.stationA = s;
  }
  
  public String getStationA (Str s) {
    return s.stationA;
  }
  
  public void setStationB (String s) {
    this.stationB = s;
  }
  
  public String getStationB (Str s) {
    return s.stationB;
  }
  
  public void setDistance (float d) {
    this.distance = d;
  }
  
  public float getDistance (Str s) {
    return s.distance;
  }
  
  public void setNextABFirst (int n) {
    this.nextABFirst = n;
  }
  
  public int getNextABFirst (Str s) {
    return s.nextABFirst;
  }
  
  public void setNextABSecond (int n) {
    this.nextABSecond = n;
  }
  
  public int getNextABSecond (Str s) {
    return s.nextABSecond;
  }
  
  public void setNextABThird (int n) {
    this.nextABThird = n;
  }
  
  public int getNextABThird (Str s) {
    return s.nextABThird;
  }
  
  public void setNextBAFirst (int n) {
    this.nextBAFirst = n;
  }
  
  public int getNextBAFirst (Str s) {
    return s.nextBAFirst;
  }
  
  public void setNextBASecond (int n) {
    this.nextBASecond = n;
  }
  
  public int getNextBASecond (Str s) {
    return s.nextBASecond;
  }
  
  public void setNextBAThird (int n) {
    this.nextBAThird = n;
  }
  
  public int getNextBAThird (Str s) {
    return s.nextBAThird;
  }
  
}

