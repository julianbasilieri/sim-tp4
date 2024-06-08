package simulacion.ClienteServidor.Models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Data
public class EventoCliente implements Evento {

    private float reloj;
    private Servidor servidor;
    private Cliente cliente;

    public EventoCliente(float reloj, Servidor servidor){
        this.reloj = reloj;
        this.servidor = servidor;
        this.cliente = new Cliente(TablaDeTrabajos.getTrabajo(),
                reloj);

    }
    @Override
    public List<Evento> avanzar() {
        List<Evento> ProxEventosAgenerar = new ArrayList<>();
        if (servidor.isOcupado()) {
            if (servidor.lugarEnColaCliente()){
                servidor.addColaClientes(cliente);}
            else {
                System.out.println("El cliente decidio irse");

            }
        } else {
            servidor.ocupar(reloj);
            ProxEventosAgenerar.add(new EventoServidor(reloj + cliente.getTiempo(), servidor, cliente));
        }
        ProxEventosAgenerar.add(new EventoCliente(reloj + generarRandomLlegada(), servidor));
        return ProxEventosAgenerar;

    }
    @Override
    public float getReloj(){
        return reloj;
    }
    public float generarRandomLlegada(){
        Random R = new Random();
        return 60 * R.nextFloat() + 30f;
    }
    public String toString(){
        return new StringBuilder().append("LLegada de Cliente; ").append(reloj).append(" Trabajo del cliente: ").append(cliente.getTrabajo().getNombre()).append(" Tiempo del Trabajo: ").append(cliente.getTiempo()).toString();


    }

}
