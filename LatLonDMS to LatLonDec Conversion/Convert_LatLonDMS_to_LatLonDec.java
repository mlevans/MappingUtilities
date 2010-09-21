package convert_latlondms_to_latlondec;

/**
*
* @author Michael Lawrence Evans :: michael@longliveman.com
*
*/

import java.io.*;

public class Main {
    BufferedReader reader = null;
	BufferedWriter writer = null;

    int ndata;

    public static void main(String[] args) {
        Main cdmslatlondec = new Main();
        cdmslatlondec.run(args);
    }

    public void run(String[] args) {
        File inDir = new File("input");
        File inFile = inDir.listFiles()[0];
        File outFile = new File("output/nylatlong.txt");

        ndata = 0;

        try {
            reader = new BufferedReader(new FileReader(inFile));
			writer = new BufferedWriter(new FileWriter(outFile));

        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        String s = null;

        s = getString();

        while (s != null) {

            try {
				writeLat(s);
				writer.write("\t", 0, 1);
				s = getString();
				writeLon(s);
				writer.newLine();
            } catch (IOException e){
                System.out.println("Error");
            }

            ndata += 1;

            s = getString();
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

    }

    String line = null;
    String[] strs = null;
    int strindx = -1;
    String s0 = null;
    String s1 = null;
    String s2 = null;

    public String getString1() {

        String s = null;
        s0 = s1;
        s1 = s2;

        if (strindx >= 0){
            s = strs[strindx];
            strindx = strindx + 1;

            if (strindx >= strs.length){
                strindx = -1;
            }
            s2 = s;
            return s;
        }

        if (line == null){
            try {
                line = reader.readLine();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
       if (line == null) {
           s2 = null;
           return null;
       }

        strs = line.split("\t");
        line = null;

        s = strs[0];

        if (strs.length == 1){
            strindx = -1;
        } else {
            strindx = 1;
        }
        s2 = s;
        return s;
    }

    public String getString() {
		String s = getString1();
		while (s != null && (s.length() == 0)) {
			s = getString1();
		}
		return s;
	}

    private double readDouble(String s) throws IOException{
        double d = Double.parseDouble(s);

        return d;
    }

    private int readInt(String s) throws IOException{
        int i = Integer.parseInt(s);

        return i;
    }

    private void writeDouble(double d) throws IOException{
		String s = Double.toString(d);
		writer.write(s, 0, s.length());
    }

    private void writeInt(int i) throws IOException{
		String s = Integer.toString(i);
		writer.write(s, 0, s.length());
    }

	private void writeLat(String s) throws IOException {
		double lat, deg, min, sec, secfrac;
		String s1;

                //if(s.compareTo("40064462077") == 0){
                  //  writer.write("NA", 0, 2);
                  //  return;
               // }

		deg = Double.parseDouble(s.substring(0,2));
		min = Double.parseDouble(s.substring(2,4));
		sec = Double.parseDouble(s.substring(4,6));
		secfrac = Double.parseDouble(s.substring(6,11));
		sec = sec + (secfrac * 0.00001);
		lat = deg + (min / 60.0) + (sec / 3600.0);
		s1 = Double.toString(lat);
		writer.write(s1, 0, s1.length());
	}

	private void writeLon(String s) throws IOException {
		double lon, deg, min, sec, secfrac;
		String s1;

                if(s.compareTo("077311046324W") == 0){
                    writer.write("NA", 0, 2);
                    return;
                }

		deg = Double.parseDouble(s.substring(0,3));
		min = Double.parseDouble(s.substring(3,5));
		sec = Double.parseDouble(s.substring(5,7));
		secfrac = Double.parseDouble(s.substring(7,12));
		sec = sec + (secfrac * 0.00001);
		lon = deg + (min / 60.0) + (sec / 3600.0);
		lon = -lon;
		s1 = Double.toString(lon);
		writer.write(s1, 0, s1.length());
	}

}