package com.abaco.ageneral;

import java.util.Date;

import com.abaco.entidad.EUsuario;

import lombok.Data;

public @Data class EPoliza {
	
	private int codigoCiaSeguro;
	private String numeroPoliza;
	private int correlativoPoliza;
	private int tipoPoliza;
	private int codigoBrocker;
	private Date fechaInicioPoliza;
	private Date fechaVencimientoPoliza;
	private double valorPoliza;
	private String numeroEndoso;
	private Date fechaEndoso;
	private int codigoClienteUltimoEndoso;
	private String nombreClienteUltimoEndoso;
	private int servicioGarantia;
	private long codigoGarantia;
	private int codigoMonedaGarantia;
	private int sucursal;
	private int agenciaEmisora;
	private int departamentoEmisor;
	private EUsuario usuarioRegistro;
	private Date fechaIngreso;
	private Date fechaUltMovimiento;
	private Date fechaEjecucion;
	private Date fechaRemate;
	private Date fechaRenovacion;
	private int estado; //0: Vigente ; 1: Cancelado
	private int ubicacionDocumentoCartera;
	
	
	
	//Adicional
	private int codigoPoliza;
	private int codigoMonedaPrimaNeta;
	private long numeroOperacion;
	private double montoPrimaNetaAnual;
	private Date fechaOrdenPago;
	private String descripcionCiaSeguro;
	private String descripcionTipoPoliza;
	private String descripcionBrockerSeguro;
	private String abreviacionMonedaPrimaNeta;
	private String descripcionMonedaPrimaNeta;
	private String abreviacionMonedaPoliza;
	private String descripcionMonedaPoliza;
	private String descripcionEstadoPoliza;
	
	
	

}
