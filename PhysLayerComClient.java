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
			System.out.printf("Received 32 bytes: %20X",fourb);

		}catch(Exception e){
			System.out.println("Error connecting to Server." + e);
		}
	}
	public static String fiveBtoFourB(String fiveB){
		String fourB="";
		for(int i = 0; i < fiveB.length();i = i+5){
			if (fiveB.substring(i, i + 5).equals("11110")) { fourB = fourB + "0"; }		 // 0000
			else if (fiveB.substring(i, i + 5).equals("01001")) { fourB = fourB + "1"; } // 0001
			else if (fiveB.substring(i, i + 5).equals("10100")) { fourB = fourB + "2"; } // 0010
			else if (fiveB.substring(i, i + 5).equals("10101")) { fourB = fourB + "3"; } // 0011
			else if (fiveB.substring(i, i + 5).equals("01010")) { fourB = fourB + "4"; } // 0100
			else if (fiveB.substring(i, i + 5).equals("01011")) { fourB = fourB + "5"; } // 0101
			else if (fiveB.substring(i, i + 5).equals("01110")) { fourB = fourB + "6"; } // 0110
			else if (fiveB.substring(i, i + 5).equals("01111")) { fourB = fourB + "7"; } // 0111
			else if (fiveB.substring(i, i + 5).equals("10010")) { fourB = fourB + "8"; } // 1000
			else if (fiveB.substring(i, i + 5).equals("10011")) { fourB = fourB + "9"; } // 1001
			else if (fiveB.substring(i, i + 5).equals("10110")) { fourB = fourB + "A"; } // 1010
			else if (fiveB.substring(i, i + 5).equals("10111")) { fourB = fourB + "B"; } // 1011
			else if (fiveB.substring(i, i + 5).equals("11010")) { fourB = fourB + "C"; } // 1100
			else if (fiveB.substring(i, i + 5).equals("11011")) { fourB = fourB + "D"; } // 1101
			else if (fiveB.substring(i, i + 5).equals("11100")) { fourB = fourB + "E"; } // 1110
			else if (fiveB.substring(i, i + 5).equals("11101")) { fourB = fourB + "F"; } // 1111
		}
		return fourB;
	}
}