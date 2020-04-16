package entidades;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class dataInDTO {

	   public long		numero_cliente;
	   public String	sucursal;;
	   public String	motivo;
	   public String	sub_motivo;
	   public String	trabajo;
	   public String	observaciones;
	   public int		tema;
	   public String	canal;
	   public String	nroReclamo;
	   public String	tarifa;
	   public String	origen;
	   public String	sAux;
	   
	   public dataInDTO(interfaceDTO regIn, String sTipoSol) {
		   String s = regIn.data_in;
		   JSONParser parser = new JSONParser();

	      try{
	    	  
	          Object obj = parser.parse(s);
	          JSONArray array = (JSONArray)obj;

	          JSONObject obj1 = (JSONObject)array.get(0);
	          if(sTipoSol.equals("CONTACTO")) {
	        	  /*
		          this.numero_cliente = (Long) obj1.get("numero_cliente");
		          this.sucursal=(String) obj1.get("sucursal");
		          this.motivo=(String) obj1.get("motivo");
		          this.sub_motivo=(String) obj1.get("sub_motivo");
		          this.trabajo=(String) obj1.get("trabajo");
		          this.observaciones=(String) obj1.get("observaciones");
		          */
		          this.numero_cliente = Long.parseLong((String) obj1.get("numeroSuministro"));
		          //this.sucursal=(String) obj1.get("sucursal");
		          this.motivo=(String) obj1.get("motivo");
		          this.sub_motivo=(String) obj1.get("subMotivo");
		          //this.trabajo=(String) obj1.get("trabajo");
		          // #4 text = text.replaceAll("\\r\\n|\\r|\\n", " ");
		          sAux = (String) obj1.get("comentarios");
		          if(sAux != null)
		        	  this.observaciones= sAux.replaceAll("\\r\\n|\\r|\\n", " ");
	        	  
	          }else if(sTipoSol.equals("CALCOM")) {
	        	  this.numero_cliente = (Long) obj1.get("numero_cliente");
	        	  this.sucursal = (String) obj1.get("sucursal");
	        	  this.tema = Integer.parseInt( (String) (obj1.get("tema")));
	        	  this.motivo = (String) obj1.get("motivo");
	        	  this.canal = (String) obj1.get("canal");
	        	  this.nroReclamo = (String) obj1.get("nro_reclamo");
	        	  this.tarifa = (String) obj1.get("tarifa");
	        	  this.origen = (String) obj1.get("origen");
	          }else {
	        	  System.out.println("Tipo de Solicitud Comercial Desconocida.");
	          }
	          
	      }catch(ParseException pe){
	          System.out.println("position: " + pe.getPosition());
	          System.out.println(pe);
	       }		
	   }
	   
}
