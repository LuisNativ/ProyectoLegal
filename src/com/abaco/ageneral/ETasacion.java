package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ETasacion {
	
	/*Data de F9224*/
	private int servicioGarantia;
	private long codigoGarantia;
	private int codigoMoneda;
	private int secuenciaTasacion;
	private long informeTasacion;
	private Date fechaTasacion;
	private double montoValorizacion;
	private long codigoTasador;
	private String comentario;
	private Date fechaMovimientoTasacion;
	private String horaMovimientoTasacion;
	private EUsuario usuarioMovimientoTasacion;
	

	//Adicional
	private String descripcionTasador;
	private String abreviacionMoneda;
	private String descripcionMoneda;

	

}
