package com.abaco.ageneral;

import java.util.Date;
import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EFlagReqLegal {
	
	/*Data de GESTIONDOC.DETALLEFLAGREQUISITOLEGAL*/
	private long numeroSolicitud;
	private int numeroFlag;
	private String descripcionFlag;
	private int modoIngresoFlag;
	private int actualizacionFlag;
	private EUsuario usuarioRegistro;
	private Date fechaRegistro;
	private String horaRegistro;
	
	//Adicional
	private String descripcionModoIngresoFlag;
	private String descripcionActualizacionFlag;
	private int indicadorBloqueoRegistral;
	private int indicadorCartaFianza;
	private int indicadorVisualizarAccion;



}
