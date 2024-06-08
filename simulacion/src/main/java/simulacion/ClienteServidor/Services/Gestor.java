package simulacion.ClienteServidor.Services;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simulacion.ClienteServidor.Dtos.TrabajoDto;
import simulacion.ClienteServidor.Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Service
public class Gestor {
    @Getter
    private Servidor servidor;
    public List<Evento> ColaDeEventos;


    public void simular(float tiempoFinal){
        ColaDeEventos = new ArrayList<>();
        servidor = new Servidor();

        //primer evento de la simulacion
        ColaDeEventos.add(new EventoCliente(generarRandomLlegada(), servidor));
        Evento Actual = ColaDeEventos.get(0);
        for(int i = 0 ; Actual.getReloj() <= tiempoFinal ; i++){
            Actual = ColaDeEventos.get(i);
            List<Evento> agregados = Actual.avanzar();
            System.out.println(Actual);
            System.out.println(servidor);
            if(!agregados.isEmpty()){
                for (Evento x : agregados) {
                    int index = i + 1;
                    while (index < ColaDeEventos.size() && x.getReloj() >= ColaDeEventos.get(index).getReloj()) {
                        index++;
                    }
                    ColaDeEventos.add(index, x);
                }

            };
        }
    }
    public float generarRandomLlegada(){
        Random R = new Random();
        return 60 * R.nextFloat() + 30f;
    }


    public void setClientes(List<TrabajoDto> trabajos) {
        TablaDeTrabajos.inicializar(trabajos);

    }

    public void setVarianza(float varianza) {
        Trabajo.setVarianza(varianza);
    }


    public void setTiempoTrabajoC(float tiempoTrabajoC) {
        TrabajoC.setTiempoEnTrabajoC(tiempoTrabajoC);
    }

    public float getReportePorcOcupacionTecnico(){
        return getServidor().reporte.getPorcOcupacionTecnico();
    }
    public float getReportePromDeTiempoEnLab(){
        return getServidor().reporte.getPromDeTiempoEnLab();
    }

}
