package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import com.abaco.entidad.EUsuario;

import lombok.Data;

public @Data class EClienteAdicional {
	private int codigoCliente;
	private int codigoTipoCliente;
	
	//Datos PJ
	private String codigoTipoPersonaJuridica;
	private double montoCapitalSocialRegistroPublicos;
	private double montoCapitalSocialActual;
	private String codigoFacultadOperar;
	private String codigoTipoSuscripcionPago;
	private int numeroSuscripcionPago;
	private int numeroAcciones;
	private String indicadorAvalarTercero;
	private String indicadorGrabarBien;
	private String descripcionAvalarTercero;
	private String observacionAvalarTercero;
	private String observacionGrabarBien;
	private String observacionConstanciaSocial;
	
	private int codigoTipoDuracionPartida;
	private String registroPartida;
	private String descripcionConstitucion;
	private int codigoTipoValorSuscripcion;
	private double montoValorNominal;
	private String descripcionAporte;
	private String estatuto;
	private String descripcionPatrimonio;
	private int codigoTipoNumeracionEstatuto;
	private int numeracionEstatuto;
	private String asiento;
	private Date fechaPeriodoInicio;
	private Date fechaPeriodoVencimiento;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
}
