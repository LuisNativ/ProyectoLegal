package com.abaco.ageneral;

import java.util.Date;

import lombok.Data;

public @Data class ERepresentanteCIAContrato {
	
	/*Data de F7408*/
	private int codigoTipoContrato;
	private long numeroContrato;
	private int codigoMonedaContrato;
	private long codigoRepresentante;
	private String nombreRepresentante;
	private String cargoRepresentante;
	private int firmadoRepresentante;
	private Date fechaFirmadoRepresentante;
	private String horaFirmado;
	private String usuarioFirmado;

	//Adicional
	private int codigoOrden;
	private int codigoAccion;
}
