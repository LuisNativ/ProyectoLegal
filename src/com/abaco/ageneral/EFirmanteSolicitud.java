package com.abaco.ageneral;

import java.util.Date;

import com.abaco.entidad.EUsuario;

import lombok.Data;

public @Data class EFirmanteSolicitud {

	private long codigoSolicitud;
	private String codigoTipoFirmante;
	private int secuenciaFirmante;
	private int dondeFirman;
	private int codigoSocioFirmante;
	private int codigoRepresentanteFirmante;
	private String nombreFirmante;
	private String direccionFirmante;
	private int codigoUbigeoFirmante;
	private String distritoFirmante;
	private String provinciaFirmante;
	private String departamentoFirmante;
	private String tipoDocumentoFirmante;
	private String descripcionTipoDocIdentidadFirmante;
	private String numeroDocumentoFirmante;
	private String estadoCivilFirmante;
	private String inscripcionPoderesFirmante;
	private Date fechaFirmaSocio;
	private Date horaRegistroFirmaSocio;
	private String horaReg;
	private String usuarioRegistroFirmaSocio;
	private int flagFirmado;
	private int codigoFirmaConyugue;
	private String firmaConyugue;
	private int codigoConyugue;
	private String nombreConyugue;
	private String direccionConyugue;
	private int codigoUbigeoConyugue;
	private String distritoConyugueFirmante;
	private String provinciaConyugueFirmante;
	private String departamentoConyugueFirmante;
	private String tipoDocumentoIDConyugue;
	private String descripcionTipoDocumentoIDConyugue;
	private String numeroDocumentoIDConyugue;
	private String inscripcionPoderesConyugue;
	private Date fechaFirmaConyugue;
	private Date horaRegistroFirmaConyugue;
	private String horaRegCony;
	private String usuarioRegistroFirmaConyugue;
	private int flagFirmadoConyugue;
	private Date fechaConfirmacion;
	private String horaConfirmacion;
	private String usuarioConfirmacion;
	private String flagConfirmacion;
	private Date fechaRegistro;
	private String horaRegistro;
	private String usuarioRegist;
	private Date fechaModificacion;
	private String horaModificacion;
	private String usuarioModificacion;
	private String descripcionTipoFirmante;
	private String descripcionTipoFirmanteLargo;
	private String okFirma;
	private String siFirma;
	private EUsuario usuarioRegistro;
	
	//Adicional (F7426)
	private int tipoPlantilla;
	private int codigoMonedaPlantilla;
	private int tipoVariante;
	private long numeroDocumento;
	
	
	
	
	
	
	
	
	

}