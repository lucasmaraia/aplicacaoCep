package aplicacaoCep;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import modelos.Cep;

public class AplicacaoCep {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {

		Boolean sair = false;

		while (sair == false) {
			Scanner sc = new Scanner(System.in);

			System.out.println("Digite o Cep desejado: ");
			String cep = sc.nextLine();

			String body = "";

			try {
				String url = "https://viacep.com.br/ws/" + cep + "/json/";
				URI endereco = URI.create(url);
				HttpClient httpClient = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
				HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
				body = response.body();

				Object obj = new JSONParser().parse(body);

				JSONObject jo = (JSONObject) obj;
				
				Cep c =  new Cep();

				c.setCep((String) jo.get("cep"));
				c.setLogradouro((String) jo.get("logradouro"));
				c.setComplemento((String) jo.get("complemento"));
				c.setBairro((String) jo.get("bairro"));
				c.setLocalidade((String) jo.get("localidade"));
				c.setUf((String) jo.get("uf"));
				c.setDdd((String) jo.get("ddd"));

				System.out.println("O cep digitado é: " + c.getCep() + " localizado no endereço " + c.getLogradouro() + " bairro: "
						+ c.getBairro()+ " estado " + c.getUf() + " e ddd: " + c.getDdd());

			} catch (Exception e) {
				System.out.println("Erro ao acessar o serviço ou cep invalido");

			}
			System.out.println("Deseja realizar outra consulta? Caso sim digite 1, caso não aperte qualquer outro numero");
			int condicao = sc.nextInt();
			if (condicao != 1) {
				sair = true;
			}
		}
		System.out.println("Programa encerrado");
	}


}
