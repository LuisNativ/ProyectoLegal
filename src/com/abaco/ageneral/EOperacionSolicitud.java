package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EOperacionSolicitud extends ESolicitud{	
	private int codigoTipoEvaluacion;
	private int codigoSeguimiento;
	
	private String codigoTipoPersona;
	private long codigoSolicitudCredito;
	private long codigoGarantia;
	private long codigoCreditoIndirecto;
	
	//private long codigoSolicitudRevision;
	
	private int codigoTipoEnvioRevision;
	private int codigoAreaRevision;
	private int codigoUsuarioRevision;
	
	private int codigoUsuarioAutorizacion;
	private String observacionAutorizacion;
	private Date fechaAutorizacion;
	private String horaAutorizacion;
	
	private List<EOperacionDocumento> lstOperacionDocumento;
	
	//Extras
	//private int indicadorConfirmar;
	private String abreviacionTipoCliente;
	private String descripcionTipoCliente;
	private String descripcionTipoEvaluacion;
	private String nombreUsuarioAutorizacion;
	private String abreviacionTipoProducto;
	private int codigoTipoProducto;
	private int codigoTipoCredito;
	private int codigoDestinoCredito;
	private int codigoStatus;
	private int codigoMoneda;
	private String descripcionStatus;
	private Date fechaRevision;
	private int tipoFlag;
	private int numeroFlag;
	private String nombreFlag;
	private String descripcionFlag;
	private int valorFlag;
	private int actualizacionFlag;
	private String descripcionValorFlag;
	private String descripcionActualizacionFlag;
	private String descripcionTipoProducto;
	private String descripcionTipoCredito;
	private String descripcionDestinoCredito;
	private double montoAprobado;
	private double montoSolicitado;
	private String abreviacionMoneda;
	private String descripcionMoneda;
	private int numeroContratoVehicular;
	private double tasaAprobada;
	private double tasaSolicitada;
	private int tipoTasaAprobada;
	private int tipoTasaSolicitada;
	private int plazoTotalAprobado;
	private int plazoTotalSolicitado;

	//Adicional
	private String observacionConformidad;
	private int secuenciaSolicitud;
	private String horaRegistroObservacion;
	
	
	
}
