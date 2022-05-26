import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.LinkedList;
import java.io.*;


public class Main {

	public static long deslocamentoToPosition(int deslocamento, LamportClock[] clocks){
		switch(deslocamento){
			case 2:
				return 0;
			case 4:
				return 1;
			case 20:
				return 2;
			case 5:
				return 3;
		}
		return 0;
	}

	public static long deslocamentoToId(int deslocamento, LamportClock[] clocks){
		for(int i=0;i<4;i++){
			if(clocks[i].getDeslocamento()==deslocamento){
				return clocks[i].getId();
			}
		}
		return 0;
	}

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java Main (number of processses) [filename of commands]");
            return;
        }

        // TODO: add support for reading commands from a file

        String input;

        try {
            int n = Integer.parseInt(args[0]);
            LamportClock[] clocks = new LamportClock[n];
            System.setProperty("java.net.preferIPv4Stack" , "true");
            InetAddress group = InetAddress.getByName("224.255.255.255");
            for (int i = 0; i < n; ++i) {
				System.out.println(i);
                int port = 8888;
				int deslocamento = Integer.parseInt(args[i+1]);
                LamportClock lc = new LamportClock(group, port, i, deslocamento);
                lc.start();
                clocks[i] = lc;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			String messageContent = "";
			System.out.println("ENTROU NO ENVIO");
			System.out.println(clocks[0].getDeslocamento());
			System.out.println(clocks[1].getDeslocamento());
			System.out.println(clocks[2].getDeslocamento());
			System.out.println(clocks[3].getDeslocamento());
			//SEQUÊNCIA DE ENVIO DE MENSGAENS
			//SEND P1 -> P2
			Event SP1P2 = new Event(1, deslocamentoToId(2,clocks), deslocamentoToId(4,clocks), messageContent);
			clocks[(int) deslocamentoToPosition(2,clocks)].updateTime(SP1P2);
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P3
			clocks[(int) deslocamentoToPosition(20,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//SEND P2 -> P3
			Event SP2P3 = new Event(1, deslocamentoToId(4,clocks), deslocamentoToId(20,clocks), messageContent);
			clocks[(int) deslocamentoToPosition(4,clocks)].updateTime(SP2P3);
			TimeUnit.SECONDS.sleep(3);
			//SEND P4 -> p2
			Event SP4P2 = new Event(1, deslocamentoToId(5,clocks), deslocamentoToId(4,clocks), messageContent);
			clocks[(int) deslocamentoToPosition(5,clocks)].updateTime(SP4P2);
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P4
			clocks[(int) deslocamentoToPosition(5,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P4
			clocks[(int) deslocamentoToPosition(5,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P1
			clocks[(int) deslocamentoToPosition(2,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P1
			clocks[(int) deslocamentoToPosition(2,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//SEND P3 -> P4
			Event SP3P4 = new Event(1, deslocamentoToId(20,clocks), deslocamentoToId(5,clocks), messageContent);
			clocks[(int) deslocamentoToPosition(5,clocks)].updateTime(SP3P4);
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P4
			clocks[(int) deslocamentoToPosition(5,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//SEND P3 -> P1
			Event SP3P1 = new Event(1, deslocamentoToId(20,clocks), deslocamentoToId(2,clocks), messageContent);
			clocks[(int) deslocamentoToPosition(5,clocks)].updateTime(SP3P1);
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P3
			clocks[(int) deslocamentoToPosition(20,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P2
			clocks[(int) deslocamentoToPosition(4,clocks)].localEvent();
			TimeUnit.SECONDS.sleep(3);
			//LOCAL P2
			clocks[(int) deslocamentoToPosition(4,clocks)].localEvent();

            List<String> ev1 = new LinkedList<String>();
			List<String> ev2 = new LinkedList<String>();
            List<String> ev3 = new LinkedList<String>();
            List<String> ev4 = new LinkedList<String>();

            ev1 = clocks[0].retorna_eventos();
			ev2 = clocks[1].retorna_eventos();
			ev3 = clocks[2].retorna_eventos();
            ev4 = clocks[3].retorna_eventos();

			//Fluxo de saida de um arquivo
			OutputStream os = new FileOutputStream("lista_eventos.txt"); // nome do arquivo que será escrito
			Writer wr = new OutputStreamWriter(os); // criação de um escritor
			BufferedWriter br = new BufferedWriter(wr); // adiciono a um escritor de buffer

			System.out.println("----------- LISTA DE EVENTOS ------------\n");

			for(int i=0; i<ev1.size(); i++) {
				System.out.println(ev1.get(i));
				br.write(ev1.get(i));
				br.newLine();
			}

			for(int i=0; i<ev2.size(); i++) {
				System.out.println(ev2.get(i));
				br.write(ev2.get(i));
				br.newLine();
			}

			for(int i=0; i<ev3.size(); i++) {
				System.out.println(ev3.get(i));
				br.write(ev3.get(i));
				br.newLine();
			}

			for(int i=0; i<ev4.size(); i++) {
				System.out.println(ev4.get(i));
				br.write(ev4.get(i));
				br.newLine();
			}

			br.close();

	

			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        } catch(Exception e) {
            System.err.println(e);
            return;
        }
    }

}