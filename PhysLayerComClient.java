import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PhysLayerComClient{
	public static void main(String[] args){
		try(Socket socket = new Socket("18.221.102.182", 38002)){
			//Socket IO
			System.out.println("Connected to Server.");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			//Read in Preamble
			double baseline = 0.0;
			for(int i = 0; i < 64; i++){
				baseline = baseline + is.read();
			}
			//Get baseline
			baseline = baseline/64.00;
			System.out.printf("Baseline established from preabmle: %.2f%n",baseline);

			//Read in bits and see if > base = 1 else = 0
			int[] bits = new int[320];
			double data = 0.0;
			for(int i = 0; i < bits.length;i++){
				data = is.read();
				if(data > baseline){
					bits[i] = 1;
				}else{
					bits[i] = 0;
				}
			}
			for(int i = 0; i < bits.length;i++){
				if(i %5 == 0)
					System.out.print("\n");
				System.out.print(bits[i]);
			}
			//Convert to NRZI
			String fiveb = "";
			fiveb = fiveb + Integer.toString(bits[0]);
			for(int i = 1; i < 320;i++){
				if(bits[i] == bits[i-1])
					fiveb= fiveb + "0";
				else
					fiveb=fiveb + "1";
			}
			String fourb = fiveBtoFourB(fiveb);
			System.out.printf("Received 32 bytes: %s%n",fourb);



		}catch(Exception e){
			System.out.println("Error connecting to Server." + e);
		}
	}
	public static String fiveBtoFourB(String fiveb){
		String fourb="";
		for(int i = 0; i < fiveb.length();i = i+5){
			if (fiveb.substring(i, i + 5).equals("11110")) { fourb = fourb + "0"; }		 // 0000
			else if (fiveb.substring(i, i + 5).equals("01001")) { fourb = fourb + "1"; } // 0001
			else if (fiveb.substring(i, i + 5).equals("10100")) { fourb = fourb + "2"; } // 0010
			else if (fiveb.substring(i, i + 5).equals("10101")) { fourb = fourb + "3"; } // 0011
			else if (fiveb.substring(i, i + 5).equals("01010")) { fourb = fourb + "4"; } // 0100
			else if (fiveb.substring(i, i + 5).equals("01011")) { fourb = fourb + "5"; } // 0101
			else if (fiveb.substring(i, i + 5).equals("01110")) { fourb = fourb + "6"; } // 0110
			else if (fiveb.substring(i, i + 5).equals("01111")) { fourb = fourb + "7"; } // 0111
			else if (fiveb.substring(i, i + 5).equals("10010")) { fourb = fourb + "8"; } // 1000
			else if (fiveb.substring(i, i + 5).equals("10011")) { fourb = fourb + "9"; } // 1001
			else if (fiveb.substring(i, i + 5).equals("10110")) { fourb = fourb + "A"; } // 1010
			else if (fiveb.substring(i, i + 5).equals("10111")) { fourb = fourb + "B"; } // 1011
			else if (fiveb.substring(i, i + 5).equals("11010")) { fourb = fourb + "C"; } // 1100
			else if (fiveb.substring(i, i + 5).equals("11011")) { fourb = fourb + "D"; } // 1101
			else if (fiveb.substring(i, i + 5).equals("11100")) { fourb = fourb + "E"; } // 1110
			else if (fiveb.substring(i, i + 5).equals("11101")) { fourb = fourb + "F"; } // 1111
		}
		return fourb;
	}
}