import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PhysLayerComClient{
	public static void main(String[] args){
		try(Socket socket = new Socket("18.221.102.182", 38002)){
			System.out.println("Connected to Server.");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			//Read in Preamble
			double baseline = 0.0;
			for(int i = 0; i < 64; i++){
				baseline = baseline + is.read();
			}
			baseline = baseline/64;
			System.out.printf("Baseline established from preabmle: %.2f%n",baseline);

		}catch(Exception e){
			System.out.println("Error connecting to Server." + e);
		}
	}
}