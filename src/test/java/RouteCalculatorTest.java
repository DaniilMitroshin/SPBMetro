import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    private List<Station> stations;
    private StationIndex kaifMetro;
    private RouteCalculator Rcalculator;
    @Override
    protected void setUp() throws Exception {
        stations = new ArrayList<>();
        Line line1 = new Line(1,"Первая");
        stations.add(new Station("Митрошинская", line1));
        line1.addStation(stations.get(0));
        stations.add(new Station("Данииловская", line1));
        line1.addStation(stations.get(1));
        stations.add(new Station("Лесная", line1));
        line1.addStation(stations.get(2));
        Line line2 = new Line(2,"Вторая");
        stations.add(new Station("Академическая", line2));
        line2.addStation(stations.get(3));
        stations.add(new Station("Красивая", line2));
        line2.addStation(stations.get(4));
        stations.add(new Station("Крутая", line2));
        line2.addStation(stations.get(5));
        Line line3 = new Line(3,"Третья");
        stations.add(new Station("Чильная", line3));
        line3.addStation(stations.get(6));
        stations.add(new Station("Улетная", line3));
        line3.addStation(stations.get(7));
        stations.add(new Station("Отменная", line3));
        line3.addStation(stations.get(8));
        stations.add(new Station("Удельная", line3));
        line3.addStation(stations.get(9));
        Line line4 = new Line(4,"Четвертая");
        stations.add(new Station("4-1", line4));//10
        line4.addStation(stations.get(10));
        stations.add(new Station("4-2", line4));
        line4.addStation(stations.get(11));
        stations.add(new Station("4-3", line4));//12
        line4.addStation(stations.get(12));

        List<Station> perehod12 = new ArrayList<>();
        perehod12.add(stations.get(2));
        perehod12.add(stations.get(3));
        List<Station> perehod23 = new ArrayList<>();
        perehod23.add(stations.get(5));
        perehod23.add(stations.get(6));
        List<Station> perehod34 = new ArrayList<>();
        perehod34.add(stations.get(9));
        perehod34.add(stations.get(10));
        List<Station> perehod41 = new ArrayList<>();
        perehod41.add(stations.get(12));
        perehod41.add(stations.get(0));
        kaifMetro = new StationIndex();
        kaifMetro.addLine(line1);
        kaifMetro.addLine(line2);
        kaifMetro.addLine(line3);
        stations.forEach(st -> kaifMetro.addStation(st));
        kaifMetro.addConnection(perehod12);
        kaifMetro.addConnection(perehod23);
        kaifMetro.addConnection(perehod34);
        kaifMetro.addConnection(perehod41);
        Rcalculator = new RouteCalculator(kaifMetro);
    }

    public void testCalculateDuration(){
        double actual = RouteCalculator.calculateDuration(stations);
        double expected = 33;
        assertEquals(expected,actual);
    }
    public void testCalculateDuration1(){
        List<Station> st = new ArrayList<>();
        st.add(stations.get(0));
        st.add(stations.get(1));
        double actual = RouteCalculator.calculateDuration(st);
        double expected = 2.5;
        assertEquals(expected,actual);
    }

    public void testGetShortestRoute1(){
        Station from = stations.get(0);
        Station to = stations.get(5);
        List<Station> ActualRes = new ArrayList<>(Rcalculator.getShortestRoute(from, to));
        List<Station> ExpectedRes = new ArrayList<>();
        ExpectedRes.add(stations.get(0));
        ExpectedRes.add(stations.get(1));
        ExpectedRes.add(stations.get(2));
        ExpectedRes.add(stations.get(3));
        ExpectedRes.add(stations.get(4));
        ExpectedRes.add(stations.get(5));
        assertEquals(ExpectedRes,ActualRes);
    }
    public void testGetShortestRoute2(){
        Station from = stations.get(0);
        Station to = stations.get(8);
        List<Station> ActualRes = new ArrayList<>(Rcalculator.getShortestRoute(from, to));
        List<Station> ExpectedRes = new ArrayList<>();
        ExpectedRes.add(stations.get(0));
        ExpectedRes.add(stations.get(12));
        ExpectedRes.add(stations.get(11));
        ExpectedRes.add(stations.get(10));
        ExpectedRes.add(stations.get(9));
        ExpectedRes.add(stations.get(8));
        assertEquals(ExpectedRes,ActualRes);
    }
    public void testGetShortestRoute3(){
        Station from = stations.get(0);
        Station to = stations.get(2);
        List<Station> ActualRes = new ArrayList<>(Rcalculator.getShortestRoute(from, to));
        List<Station> ExpectedRes = new ArrayList<>();
        ExpectedRes.add(stations.get(0));
        ExpectedRes.add(stations.get(1));
        ExpectedRes.add(stations.get(2));

        assertEquals(ExpectedRes,ActualRes);
    }
    public void testGetShortestRoute4(){
        Station from = stations.get(0);
        Station to = stations.get(0);
        List<String> ActualRes = new ArrayList<>();
        Rcalculator.getShortestRoute(from, to).forEach(e -> ActualRes.add(e.getName()));
        List<String> ExpectedRes = new ArrayList<>();
        ExpectedRes.add(stations.get(0).getName());
        assertEquals(ExpectedRes,ActualRes);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
