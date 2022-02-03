package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EComprobanteGarantia {
	
	private long codigoSolicitud;
	private int secuenciaGarantia;
	private int secuenciaDetalle;
	private String numeroComprobantePago;
	private Date fechaComprobantePago;
	private int codigoMonedaComprobantePago;
	private double importeComprobantePago;
	private String descripcionComprobantePago;
	private Date fechaActualizacion;
	private String horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Adicional
	private String abreviacionMonedaComprobante;
	private String descripcionMonedaComprobante;

}
