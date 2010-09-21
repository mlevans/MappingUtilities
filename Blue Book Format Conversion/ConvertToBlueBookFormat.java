package converttobluebookformat;

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
        Main cbbf = new Main(); //cbbf = convert blue book format
        cbbf.run(args);
    }

    public void run(String[] args) {
        File inDir = new File("input");
        File inFile = inDir.listFiles()[0];
        File outFile = new File("output/output.txt");

        ndata = 0;

        try {
            reader = new BufferedReader(new FileReader(inFile));
			writer = new BufferedWriter(new FileWriter(outFile));

        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        String s = null;
        String sx = null;
        String sy = null;

        s = getString();

        while (s != null) {

            try {
				sx = s;
				sy = getString();
				if ((sx.compareTo("0") == 0) ||
				    (sy.compareTo("0") == 0)) {
					writeNonDataRecord(ndata);
				} else {
					writeDataRecord(sx, sy, ndata);
				}
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

	private void writeNonDataRecord(int ndata) throws IOException {
		String s;

		writer.write("      ", 0, 6); // C01-06
		writer.write("*81*", 0, 4);   // C07-10
		writer.write("   ", 0, 3);    // C11-13
		writer.write(" ", 0, 1);      // C14
		writer.write("NODATA ", 0, 7); // C15-21
		s = getIntString(ndata, 5);
		writer.write(s, 0, 5);        // C22-26
		writer.write("                  ", 0, 18); // C27-44
		writer.write("0000001000", 0, 10);		  // C45-54
		writer.write("00000001000", 0, 11);		  // C55-65
		writer.write("3104", 0, 4);	  // C66-69
		writer.write("           ", 0, 11); // C70-80
		writer.newLine();
	}

	private void writeDataRecord(String sx, String sy, int ndata)
		throws IOException {

		String s;

		writer.write("      ", 0, 6); // C01-06
		writer.write("*81*", 0, 4);   // C07-10
		writer.write("   ", 0, 3);    // C11-13
		writer.write(" ", 0, 1);      // C14
		writer.write("DATUM ", 0, 6); // C15-20
		s = getIntString(ndata, 5);
		writer.write(s, 0, 5);        // C21-25
		writer.write("                   ", 0, 19); // C26-44
		s = getEastingString(sx);
		writer.write(s, 0, 10);		  // C45-54
		s = getNorthingString(sy);
		writer.write(s, 0, 11);		  // C55-65
		writer.write("3104", 0, 4);	  // C66-69
		writer.write("           ", 0, 11); // C70-80
		writer.newLine();
	}

	private String getIntString(int n, int length) {
		StringBuffer sbuf = new StringBuffer();
		String s;
		int l;

		s = Integer.toString(n);
		l = length - s.length();
		if (l < 0) {
			System.out.println("int string too long!");
		}
		while (l > 0) {
			sbuf.append("0");
			l = l - 1;
		}
		sbuf.append(s);
		return sbuf.toString();
	}

	private String getEastingString(String sx) {
		StringBuffer sbuf = new StringBuffer();
		double d = 0.0, dint, dfrac;
		long longd;
		int l;
		String sint, sfrac;

		try {
			d = readDouble(sx);
		} catch (IOException e) {
			System.out.println(e);
		}
		if (d < 0.0) {
			System.out.println("Easting negative!");
		}
		d = d * 0.3048;
		d = d + 0.0005;
		longd = (long) d;
		sint = Long.toString(longd);
		dint = (double) longd;
		dfrac = d - dint;
		dfrac = dfrac * 1000.0;
		longd = (long) dfrac;
		sfrac = Long.toString(longd);
		l = 7 - sint.length();
		if (l < 0) {
			System.out.println("Easting int string too long!");
		}
		while (l > 0) {
			sbuf.append("0");
			l = l - 1;
		}
		sbuf.append(sint);
		l = sfrac.length();
		while (l < 3) {
			sbuf.append("0");
			l = l + 1;
		}
		sbuf.append(sfrac);
		return sbuf.toString();
	}

	private String getNorthingString(String sy) {
		StringBuffer sbuf = new StringBuffer();
		double d = 0.0, dint, dfrac;
		long longd;
		int l;
		String sint, sfrac;

		try {
			d = readDouble(sy);
		} catch (IOException e) {
			System.out.println(e);
		}
		if (d < 0.0) {
			System.out.println("Northing negative!");
		}
		d = d * 0.3048;
		d = d + 0.0005;
		longd = (long) d;
		sint = Long.toString(longd);
		dint = (double) longd;
		dfrac = d - dint;
		dfrac = dfrac * 1000.0;
		longd = (long) dfrac;
		sfrac = Long.toString(longd);
		l = 8 - sint.length();
		if (l < 0) {
			System.out.println("Northing int string too long!");
		}
		while (l > 0) {
			sbuf.append("0");
			l = l - 1;
		}
		sbuf.append(sint);
		l = sfrac.length();
		while (l < 3) {
			sbuf.append("0");
			l = l + 1;
		}
		sbuf.append(sfrac);
		return sbuf.toString();
	}

}