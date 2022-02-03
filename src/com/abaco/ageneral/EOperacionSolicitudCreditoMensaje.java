package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.ageneral.EDocumentoCarga;
import com.abaco.ageneral.EFacultadPoder;
import com.abaco.ageneral.EOperacionDocumento;
import com.abaco.ageneral.ERepresentanteLegal;
import com.abaco.entidad.EUsuario;


public @Data class EOperacionSolicitudCreditoMensaje {
	private long numeroSolicitud;
	private int codigoTipoCliente;
	private int codigoCliente;
	
	private int codigoMensaje;
	private String descripcionMensaje;
	private String nombreUsuarioEnvio;
	private Date fechaEnvio;
	private String horaEnvio;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
}
