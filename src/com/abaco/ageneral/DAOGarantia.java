package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EDireccion;
import com.abaco.entidad.EDocumentoIdentidad;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EProductoCredito;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.ETipoCliente;
import com.abaco.entidad.ETipoDocumentoPersona;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOGarantia extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_GARANTIA_PENDIENTEREGISTRO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIA_PENDIENTEREGISTRO("+parametrosSP(73)+") }";
	private static final String SP_ABACOINLEGAL_INS_GARANTIAMANTENIMIENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIAMANTENIMIENTO("+parametrosSP(72)+") }"; 
	private static final String SP_ABACOINLEGAL_INS_UPD_GARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_GARANTIATRAMITE("+parametrosSP(38)+") }";
	private static final String SP_ABACOINLEGAL_INS_UPD_ASIENTOTRAMITEGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_ASIENTOTRAMITEGARANTIA("+parametrosSP(38)+") }";
	private static final String SP_ABACOINLEGAL_INS_GARANTIAANEXOF7325="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIAANEXOF7325("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_INS_TIPOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_TIPOGARANTIA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_INS_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_POLIZA("+parametrosSP(15)+") }";
	private static final String SP_ABACOINLEGAL_INS_SOLICITUDDOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_SOLICITUDDOCUMENTOGARANTIA("+parametrosSP(11)+") }";
	private static final String SP_ABACOINLEGAL_INS_DETALLESOLICITUDDOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_DETALLESOLICITUDDOCUMENTOGARANTIA("+parametrosSP(14)+") }";
	private static final String SP_ABACOINLEGAL_INS_INMUEBLESGARANTIAPREDIOS="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_INMUEBLESGARANTIAPREDIOS("+parametrosSP(18)+") }";
	private static final String SP_ABACOINLEGAL_INS_MAESTROCONTRATOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_MAESTROCONTRATOGARANTIA("+parametrosSP(18)+") }";
	private static final String SP_ABACOINLEGAL_INS_REPRESENTANTECIAXCONTRATO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REPRESENTANTECIAXCONTRATO("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_INS_SOLICITUDDOCUMENTODESEMBOLSOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_SOLICITUDDOCUMENTODESEMBOLSOGARANTIA("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_INS_OBSERVACIONTRAMITEOPERATIVOSOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OBSERVACIONTRAMITEOPERATIVOSOLICITUD("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_INS_DETALLEFLAGREQUISITOLEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_DETALLEFLAGREQUISITOLEGAL("+parametrosSP(7)+") }";
	private static final String SP_ABACOINLEGAL_UPD_LIBERARGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_LIBERARGARANTIA("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_UPD_GARANTIAMANTENIMIENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIAMANTENIMIENTO("+parametrosSP(70)+") }"; 
	private static final String SP_ABACOINLEGAL_UPD_GARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIASOLICITUD("+parametrosSP(21)+") }";
	private static final String SP_ABACOINLEGAL_UPD_GARANTIASOLICITUDSIAF="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIASOLICITUDSIAF("+parametrosSP(34)+") }";
	private static final String SP_ABACOINLEGAL_UPD_TIPOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_TIPOGARANTIA("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_UPD_GARANTIAPOLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIAPOLIZA("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_UPD_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_POLIZA("+parametrosSP(16)+") }";	
	private static final String SP_ABACOINLEGAL_UPD_TIPOINGRESOPORCENTAJE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_TIPOINGRESOPORCENTAJE("+parametrosSP(6)+") }";	
	private static final String SP_ABACOINLEGAL_UPD_SOLICITUDDOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_SOLICITUDDOCUMENTOGARANTIA("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_UPD_DETALLESOLICITUDDOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_DETALLESOLICITUDDOCUMENTOGARANTIA("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACIONDESEMBOLSOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACIONDESEMBOLSOGARANTIA("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_UPD_INMUEBLESGARANTIAPREDIOS="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_INMUEBLESGARANTIAPREDIOS("+parametrosSP(19)+") }";
	private static final String SP_ABACOINLEGAL_UPD_FIRMANTESDOCUMENTOSOLICITUDCONFORMIDAD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_FIRMANTESDOCUMENTOSOLICITUDCONFORMIDAD("+parametrosSP(7)+") }";
	private static final String SP_ABACOINLEGAL_UPD_FIRMANTESDOCUMENTOSOLICITUDFIRMACONTRATO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_FIRMANTESDOCUMENTOSOLICITUDFIRMACONTRATO("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_UPD_CONFIRMACIONDATOSGARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_CONFIRMACIONDATOSGARANTIASOLICITUD("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_UPD_NOCONFORMIDADGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_NOCONFORMIDADGARANTIA("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_UPD_MAESTROCONTRATOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_MAESTROCONTRATOGARANTIA("+parametrosSP(18)+") }";
	private static final String SP_ABACOINLEGAL_UPD_CONTRATOGARANTIAIMPRESION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_CONTRATOGARANTIAIMPRESION("+parametrosSP(19)+") }";
	private static final String SP_ABACOINLEGAL_UPD_FLAGIMPRESIONCONTRATO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_FLAGIMPRESIONCONTRATO("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_UPD_DETALLEFLAGREQUISITOLEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_DETALLEFLAGREQUISITOLEGAL("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIATRAMITE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIAASOCIADASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIAASOCIADASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIADETALLESOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIADETALLESOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CRED_DIR_IND_GARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CRED_DIR_IND_GARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_TIPOCAMBIODIARIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_TIPOCAMBIODIARIO() }";
	private static final String SP_ABACOINLEGAL_BUS_CIASEGUROPOLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CIASEGUROPOLIZA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_BUS_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_POLIZA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ULTIMAGARANTIAGENERADA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ULTIMAGARANTIAGENERADA() }";
	private static final String SP_ABACOINLEGAL_BUS_SOLICITUDXGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOLICITUDXGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ANEXOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ANEXOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ULTASIENTOGARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ULTASIENTOGARANTIATRAMITE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CARACTERISTICAINMUEBLEPREDIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CARACTERISTICAINMUEBLEPREDIO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_INSTRUCCIONAPROBACIONOPERACIONES="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_INSTRUCCIONAPROBACIONOPERACIONES("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ULTIMAREVISIONSOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ULTIMAREVISIONSOLICITUDCREDITO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REGISTROSOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REGISTROSOLICITUDCREDITO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_MAESTROCONTRATO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_MAESTROCONTRATO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ULTIMOCONTRATOGARANTIAGENERADO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ULTIMOCONTRATOGARANTIAGENERADO() }";
	private static final String SP_ABACOINLEGAL_BUS_MAESTRODOCUMENTOGENERADO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_MAESTRODOCUMENTOGENERADO("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_BUS_POLIZAASOCIADAGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_POLIZAASOCIADAGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAPORLIBERAR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAPORLIBERAR("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAPORCONSTITUIR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAPORCONSTITUIR("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIASOLICITUD("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAASOCIADASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAASOCIADASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIADOCUMENTOSOLICITADO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIADOCUMENTOSOLICITADO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_HISTORICOGARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_HISTORICOGARANTIASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAVIGENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAVIGENTE("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIA("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CIASEGUROPOLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CIASEGUROPOLIZA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_HISTORICOGARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_HISTORICOGARANTIATRAMITE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_ASIENTOTRAMITEGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_ASIENTOTRAMITEGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CREDITOVIGENTERELACIONADOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CREDITOVIGENTERELACIONADOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CREDITOCANCELADORELACIONADOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CREDITOCANCELADORELACIONADOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CREDITOSASOCIADOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CREDITOSASOCIADOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_DEL_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_POLIZA("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIALIBERADA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIALIBERADA("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOGARANTIA("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOXNROGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOXNROGARANTIA("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOGARANTIADETALLE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOGARANTIADETALLE("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACIONDESEMBOLSOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACIONDESEMBOLSOGARANTIA("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACIONDESEMBOLSOXNROGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACIONDESEMBOLSOXNROGARANTIA("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_SEL_TRAMITEOPERATIVOSOLICITUDLEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_TRAMITEOPERATIVOSOLICITUDLEGAL("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_INMUEBLESGARANTIAPREDIOS="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_INMUEBLESGARANTIAPREDIOS("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_DETALLEFLAGSSOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_DETALLEFLAGSSOLICITUDCREDITO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_FIRMANTESDOCUMENTOSOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_FIRMANTESDOCUMENTOSOLICITUD("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_SOLICITUDGARANTIAASOCIADA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOLICITUDGARANTIAASOCIADA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_COMPROBANTESGARANTIAASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_COMPROBANTESGARANTIAASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_MAESTROREPRESENTANTECOMPANIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_MAESTROREPRESENTANTECOMPANIA() }";
	private static final String SP_ABACOINLEGAL_SEL_REPRESENTANTECOMPANIACONTRATO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_REPRESENTANTECOMPANIACONTRATO("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_DETALLEFIRMANTESCONTRATO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_DETALLEFIRMANTESCONTRATO("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OBSERVACIONTRAMITEOPERATIVOSOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OBSERVACIONTRAMITEOPERATIVOSOLICITUD("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_DETALLEFLAGREQUISITOLEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_DETALLEFLAGREQUISITOLEGAL("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_DEL_DETALLESOLICITUDDOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_DETALLESOLICITUDDOCUMENTOGARANTIA("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_DEL_INMUEBLESGARANTIAPREDIOS="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_INMUEBLESGARANTIAPREDIOS("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_DEL_REPRESENTANTECIAXCONTRATO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_REPRESENTANTECIAXCONTRATO("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_DEL_DETALLEFLAGREQUISITOLEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_DETALLEFLAGREQUISITOLEGAL("+parametrosSP(4)+") }";


	


	
	private static String parametrosSP(int numeroDeParametros) {
        StringBuilder cadena = new StringBuilder();

        for (int i = 1; i <= numeroDeParametros; i++) {
            cadena.append("?,");
        }

        int longitudCadena = cadena.length();
        StringBuilder removeUltimoCaracter = new StringBuilder(cadena);
        StringBuilder cadenaFinal = removeUltimoCaracter.deleteCharAt(longitudCadena - 1);
        return cadenaFinal.toString();
    }
	
	public DAOGarantia(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje liberarGarantia(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			//lstParametrosEntrada.add(eGarantia.getMontoGarantizado());
			//lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eGarantia.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eGarantia.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_LIBERARGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaPendienteRegistro(EGarantiaSolicitud eGarantiaSolicitud, EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoCliente());
			lstParametrosEntrada.add(eGarantiaSolicitud.getNombreLargo());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario2());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario3());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario4());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario5());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario6());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoVariable1());
			lstParametrosEntrada.add(eGarantia.getTipoVariable2());
			lstParametrosEntrada.add(eGarantia.getTipoVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getNumeroDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getDescripcionB());
			lstParametrosEntrada.add(eGarantia.getDescripcionC());
			lstParametrosEntrada.add(eGarantia.getDescripcionD());
			lstParametrosEntrada.add(eGarantia.getComentario());
			lstParametrosEntrada.add(eGarantia.getCodigoTasador());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getFechaVencimiento());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaValoracion());
			lstParametrosEntrada.add(eGarantia.getFechaVariable1());
			lstParametrosEntrada.add(eGarantia.getFechaVariable2());
			lstParametrosEntrada.add(eGarantia.getFechaVariable3());
			lstParametrosEntrada.add(eGarantia.getFechaVariable4());
			lstParametrosEntrada.add(eGarantia.getFechaComercial());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());
			lstParametrosEntrada.add(eGarantia.getMontoOriginalGarantia());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoValoracion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoSolicitud());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getMontoVariable1());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getMontoVariable4());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getDepositario());
			lstParametrosEntrada.add(eGarantia.getAlmacen());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getClase());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getModelo());
			lstParametrosEntrada.add(eGarantia.getCarroceria());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable2());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable4());
			lstParametrosEntrada.add(eGarantia.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantia.getBancoEmisor());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuarioSIAF());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getUbicacion2());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getNumeroEndoso());
			lstParametrosEntrada.add(eGarantia.getMontoDisponible());
			lstParametrosEntrada.add(eGarantia.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantia.getTipocambio());		
			lstParametrosEntrada.add(eGarantia.getPorcentajeDisponible());
						
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIA_PENDIENTEREGISTRO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaMantenimiento(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eGarantia.getNombreCorto());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario2());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario3());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario4());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario5());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario6());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoVariable1());
			lstParametrosEntrada.add(eGarantia.getTipoVariable2());
			lstParametrosEntrada.add(eGarantia.getTipoVariable3());
			lstParametrosEntrada.add(eGarantia.getCodigoEstado());
			lstParametrosEntrada.add(eGarantia.getCodigoSituacion());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getNumeroDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getDescripcionB());
			lstParametrosEntrada.add(eGarantia.getDescripcionC());
			lstParametrosEntrada.add(eGarantia.getDescripcionD());
			lstParametrosEntrada.add(eGarantia.getComentario());
			lstParametrosEntrada.add(eGarantia.getCodigoTasador());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getFechaVencimiento());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaValoracion());
			lstParametrosEntrada.add(eGarantia.getFechaVariable1());
			lstParametrosEntrada.add(eGarantia.getFechaVariable2());
			lstParametrosEntrada.add(eGarantia.getFechaVariable3());
			lstParametrosEntrada.add(eGarantia.getFechaVariable4());
			lstParametrosEntrada.add(eGarantia.getFechaComercial());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());
			lstParametrosEntrada.add(eGarantia.getMontoOriginalGarantia());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoValoracion());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getMontoVariable1());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getMontoVariable4());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getDepositario());
			lstParametrosEntrada.add(eGarantia.getAlmacen());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getClase());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getModelo());
			lstParametrosEntrada.add(eGarantia.getCarroceria());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable2());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable4());
			lstParametrosEntrada.add(eGarantia.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantia.getBancoEmisor());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getUbicacion2());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getNumeroEndoso());
			lstParametrosEntrada.add(eGarantia.getMontoDisponible());
			lstParametrosEntrada.add(eGarantia.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantia.getTipocambio());
			lstParametrosEntrada.add(eGarantia.getPorcentajeDisponible());
			lstParametrosEntrada.add(eGarantia.getCodigoAsignacionInmueble());

			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIAMANTENIMIENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje agregarModificarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoServicio());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroHojaIngresoLegal());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngreso());
			lstParametrosEntrada.add(eGarantiaTramite.getEvaluacionDocumento());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaFirmaContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoNotaria());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaLegalizacionFirma());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroKardex());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoNotario());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistroB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsientoB());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo1());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTachaB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTituloB());
			
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistro());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsiento());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion1());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacion());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTacha());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTituloA());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioRegistro().getNombreUsuarioSIAF());	
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_GARANTIATRAMITE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso DAOGarantia: Problemas al agregar/Modificar Tramite de Garantía", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarAsientoGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoServicio());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroHojaIngresoLegal());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngreso());
			lstParametrosEntrada.add(eGarantiaTramite.getEvaluacionDocumento());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaFirmaContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoNotaria());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaLegalizacionFirma());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroKardex());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoNotario());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistroB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsientoB());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo1());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTachaB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTituloB());
			
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistro());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsiento());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion1());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacion());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTacha());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcion());	
			lstParametrosEntrada.add(eGarantiaTramite.getTituloA());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioRegistro().getNombreUsuarioSIAF());	
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_ASIENTOTRAMITEGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso DAOGarantia: Problemas al agregar/Modificar Asiento Tramite de Garantía", objEx);
		}
		return mensaje;
	}
	
	

	public EMensaje registrarGarantiaSolicitudAnexoF7325() {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIAANEXOF7325, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarTipoGarantia(EGeneral tipoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(tipoGarantia.getDescripcion());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_TIPOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarPoliza(EPoliza ePoliza) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getTipoPoliza());
			lstParametrosEntrada.add(ePoliza.getCodigoBrocker());
			lstParametrosEntrada.add(ePoliza.getFechaInicioPoliza());
			lstParametrosEntrada.add(ePoliza.getFechaVencimientoPoliza());
			lstParametrosEntrada.add(ePoliza.getValorPoliza());
			lstParametrosEntrada.add(ePoliza.getNumeroEndoso());
			lstParametrosEntrada.add(ePoliza.getFechaEndoso());
			lstParametrosEntrada.add(ePoliza.getCodigoClienteUltimoEndoso());
			lstParametrosEntrada.add(ePoliza.getNombreClienteUltimoEndoso());	
			lstParametrosEntrada.add(ePoliza.getCodigoMonedaGarantia());
			lstParametrosEntrada.add(ePoliza.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_POLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarSolicitudDocumentoGarantia(EGarantia eOGarantia, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		eOGarantia.setFechaRegistro(new Date());
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOGarantia.getTipoDocumento());
			lstParametrosEntrada.add(eOGarantia.getEstadoDocumento());
			lstParametrosEntrada.add(eOGarantia.getCodigoAreaEmisora());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());		
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_SOLICITUDDOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al insertar datos del Documento en DB2.", objEx);
		}
		return mensaje;
	}
	
	
	
	public EMensaje registrarDetalleSolicitudDocumentoGarantia(EGarantia eOGarantia,EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		eOGarantia.setFechaRegistro(new Date());
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOGarantia.getNumeroSolicitud());
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOGarantia.getNumeroSolicitudCredito());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());	
			lstParametrosEntrada.add(eOGarantia.getTipoDocumento());
			lstParametrosEntrada.add(eOGarantia.getFirmaDocumento());
			lstParametrosEntrada.add(eOGarantia.getObservacionDocumento());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_DETALLESOLICITUDDOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al insertar datos del Documento en DB2.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarInmueblesGarantiaPredios(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());

			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_INMUEBLESGARANTIAPREDIOS, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarContratoGarantiaF7401(EContrato eContrato) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eContrato.getCodigoCliente());
			lstParametrosEntrada.add(eContrato.getCodigoSolicitud());
			lstParametrosEntrada.add(eContrato.getNombreSocioCorto());
			lstParametrosEntrada.add(eContrato.getCodigoServicio());
			lstParametrosEntrada.add(eContrato.getCodigoMonedaPagare());
			lstParametrosEntrada.add(eContrato.getImporteAprobado());
			lstParametrosEntrada.add(eContrato.getTasaInteresMostrada());
			lstParametrosEntrada.add(eContrato.getCodigoPeriodo());
			lstParametrosEntrada.add(eContrato.getPlazoDias());
			lstParametrosEntrada.add(eContrato.getCodigoCompania());
			lstParametrosEntrada.add(eContrato.getCodigoRepresentante());
			lstParametrosEntrada.add(eContrato.getCodigoTipoContrato());
			lstParametrosEntrada.add(eContrato.getFirmaConyugue());
			lstParametrosEntrada.add(eContrato.getNumeroOperacion());
			lstParametrosEntrada.add(eContrato.getNumeroLista());
			lstParametrosEntrada.add(eContrato.getUsuarioRegistro().getNombreUsuario());


			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_MAESTROCONTRATOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje registrarRepresentanteCompaniaContratoF7433(ERepresentanteCIAContrato eRepresentanteCiaContrato) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getCodigoTipoContrato());
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getNumeroContrato());
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getCodigoRepresentante());
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getNombreRepresentante());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REPRESENTANTECIAXCONTRATO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje agregarSolicitudDocumentoyDesembolsoGarantia(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eGarantia.getNombreCorto());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_SOLICITUDDOCUMENTODESEMBOLSOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	

	public EMensaje agregarObservacionTramiteOperativoSolicitud(EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getNombrePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoMoneda());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoProducto());
			lstParametrosEntrada.add(eOperacionSolicitud.getObservacionConformidad());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OBSERVACIONTRAMITEOPERATIVOSOLICITUD, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eFlagRequisitoLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getDescripcionFlag());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getModoIngresoFlag());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getActualizacionFlag());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_DETALLEFLAGREQUISITOLEGAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}

	public EMensaje modificarTipoGarantia(EGeneral tipoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(tipoGarantia.getCodigo2());
			lstParametrosEntrada.add(tipoGarantia.getDescripcion());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_TIPOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarGarantiaPoliza(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIAPOLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarPoliza(EPoliza ePoliza) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			lstParametrosEntrada.add(ePoliza.getTipoPoliza());
			lstParametrosEntrada.add(ePoliza.getCodigoBrocker());
			lstParametrosEntrada.add(ePoliza.getFechaInicioPoliza());
			lstParametrosEntrada.add(ePoliza.getFechaVencimientoPoliza());
			lstParametrosEntrada.add(ePoliza.getValorPoliza());
			lstParametrosEntrada.add(ePoliza.getNumeroEndoso());
			lstParametrosEntrada.add(ePoliza.getFechaEndoso());
			lstParametrosEntrada.add(ePoliza.getCodigoClienteUltimoEndoso());
			lstParametrosEntrada.add(ePoliza.getNombreClienteUltimoEndoso());	
			lstParametrosEntrada.add(ePoliza.getCodigoMonedaGarantia());
			lstParametrosEntrada.add(ePoliza.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_POLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarTipoIngresoPorcentaje(EGarantiaSolicitud eGarantiaSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getTipoIngreso());
			lstParametrosEntrada.add(eGarantiaSolicitud.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_TIPOINGRESOPORCENTAJE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	
	
	public EMensaje modificarSolicitudDocumentoGarantia(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantia.getNumeroSolicitudCredito());
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoDocumento());
			lstParametrosEntrada.add(eGarantia.getEstadoDocumento());
			lstParametrosEntrada.add(eGarantia.getEstadoDocumentoLegal());
			lstParametrosEntrada.add(eGarantia.getEstadoDocumentoNegocio());
			lstParametrosEntrada.add(eGarantia.getEstadoDocumentoNotaria());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_SOLICITUDDOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia,int indicador) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getSecuenciaDocumento());
			lstParametrosEntrada.add(eGarantia.getTipoDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionDocumento());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eGarantia.getFirmaDocumento());
			lstParametrosEntrada.add(eGarantia.getObservacionDocumento());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(indicador);
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_DETALLESOLICITUDDOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudDesembolsoGarantia(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoDocumento());
			lstParametrosEntrada.add(eGarantia.getEstadoDesembolso());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACIONDESEMBOLSOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarInmuebleGarantiaPredios(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getNumeroSecuenciaInmueble());
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_INMUEBLESGARANTIAPREDIOS, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarConfirmacionFirmanteSolicitud(EFirmanteSolicitud eFirmanteSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eFirmanteSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eFirmanteSolicitud.getCodigoTipoFirmante());
			lstParametrosEntrada.add(eFirmanteSolicitud.getSecuenciaFirmante());
			lstParametrosEntrada.add(eFirmanteSolicitud.getFlagConfirmacion());
			lstParametrosEntrada.add(eFirmanteSolicitud.getUsuarioRegistro().getNombreUsuario());

			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_FIRMANTESDOCUMENTOSOLICITUDCONFORMIDAD, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarFirmaContratoFirmanteSolicitud(EFirmanteSolicitud eFirmanteSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eFirmanteSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eFirmanteSolicitud.getCodigoTipoFirmante());
			lstParametrosEntrada.add(eFirmanteSolicitud.getSecuenciaFirmante());
			lstParametrosEntrada.add(eFirmanteSolicitud.getFlagFirmado());
			lstParametrosEntrada.add(eFirmanteSolicitud.getFechaFirmaSocio());
			lstParametrosEntrada.add(formato.format(eFirmanteSolicitud.getHoraRegistroFirmaSocio()));
			lstParametrosEntrada.add(eFirmanteSolicitud.getFlagFirmadoConyugue());
			lstParametrosEntrada.add(eFirmanteSolicitud.getFechaFirmaConyugue());
			lstParametrosEntrada.add(formato.format(eFirmanteSolicitud.getHoraRegistroFirmaConyugue()));
			lstParametrosEntrada.add(eFirmanteSolicitud.getUsuarioRegistro().getNombreUsuario());

			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_FIRMANTESDOCUMENTOSOLICITUDFIRMACONTRATO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarConfirmaDatosGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getUsuarioRegistro().getNombreUsuario());
		
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_CONFIRMACIONDATOSGARANTIASOLICITUD, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	} 
	
	public EMensaje modificarNoConformidadDatosGarantia(long codigoSolicitud,String nombreUusario) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			lstParametrosEntrada.add(nombreUusario);
		
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_NOCONFORMIDADGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarContratoGarantiaF7401(EContrato eContrato) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eContrato.getNumeroContrato());
			lstParametrosEntrada.add(eContrato.getCodigoCliente());
			lstParametrosEntrada.add(eContrato.getNombreSocioCorto());
			lstParametrosEntrada.add(eContrato.getCodigoServicio());
			lstParametrosEntrada.add(eContrato.getCodigoMonedaPagare());
			lstParametrosEntrada.add(eContrato.getImporteAprobado());
			lstParametrosEntrada.add(eContrato.getTasaInteresMostrada());
			lstParametrosEntrada.add(eContrato.getCodigoPeriodo());
			lstParametrosEntrada.add(eContrato.getPlazoDias());
			lstParametrosEntrada.add(eContrato.getCodigoCompania());
			lstParametrosEntrada.add(eContrato.getCodigoRepresentante());
			lstParametrosEntrada.add(eContrato.getCodigoTipoContrato());
			lstParametrosEntrada.add(eContrato.getFirmaConyugue());
			lstParametrosEntrada.add(eContrato.getNumeroOperacion());
			lstParametrosEntrada.add(eContrato.getNumeroLista());
			lstParametrosEntrada.add(eContrato.getUsuarioRegistro().getNombreUsuario());


			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_MAESTROCONTRATOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarImpresionContratoGarantia(EContrato eContrato) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eContrato.getNumeroContrato());
			lstParametrosEntrada.add(eContrato.getCodigoSolicitud());
			lstParametrosEntrada.add(eContrato.getCodigoCliente());
			lstParametrosEntrada.add(eContrato.getNombreSocioCorto());
			lstParametrosEntrada.add(eContrato.getCodigoServicio());
			lstParametrosEntrada.add(eContrato.getCodigoMonedaPagare());
			lstParametrosEntrada.add(eContrato.getImporteAprobado());
			lstParametrosEntrada.add(eContrato.getTasaInteresMostrada());
			lstParametrosEntrada.add(eContrato.getCodigoPeriodo());
			lstParametrosEntrada.add(eContrato.getPlazoDias());
			lstParametrosEntrada.add(eContrato.getCodigoCompania());
			lstParametrosEntrada.add(eContrato.getCodigoRepresentante());
			lstParametrosEntrada.add(eContrato.getCodigoTipoContrato());
			lstParametrosEntrada.add(eContrato.getFirmaConyugue());
			lstParametrosEntrada.add(eContrato.getNumeroOperacion());
			lstParametrosEntrada.add(eContrato.getNumeroLista());
			lstParametrosEntrada.add(eContrato.getUsuarioRegistro().getNombreUsuario());


			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_CONTRATOGARANTIAIMPRESION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarFlagImpresionContrato(EContrato eContrato) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eContrato.getTipoPlantilla());
			lstParametrosEntrada.add(eContrato.getMonedaPlantilla());
			lstParametrosEntrada.add(eContrato.getTipovariante());
			lstParametrosEntrada.add(eContrato.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eContrato.getCodigoSolicitud());
			lstParametrosEntrada.add(eContrato.getIndicadorConsulta());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_FLAGIMPRESIONCONTRATO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eFlagRequisitoLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getNumeroFlag());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getDescripcionFlag());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getModoIngresoFlag());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getActualizacionFlag());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_DETALLEFLAGREQUISITOLEGAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaMantenimiento(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			//lstParametrosEntrada.add(eGarantia.getCodigoCliente());
			//lstParametrosEntrada.add(eGarantia.getNombreCorto());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario2());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario3());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario4());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario5());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario6());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			//lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoVariable1());
			lstParametrosEntrada.add(eGarantia.getTipoVariable2());
			lstParametrosEntrada.add(eGarantia.getTipoVariable3());
			lstParametrosEntrada.add(eGarantia.getCodigoEstado());
			lstParametrosEntrada.add(eGarantia.getCodigoSituacion());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getNumeroDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getDescripcionB());
			lstParametrosEntrada.add(eGarantia.getDescripcionC());
			lstParametrosEntrada.add(eGarantia.getDescripcionD());
			lstParametrosEntrada.add( eGarantia.getComentario());
			lstParametrosEntrada.add(eGarantia.getCodigoTasador());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getFechaVencimiento());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaValoracion());
			lstParametrosEntrada.add(eGarantia.getFechaVariable1());
			lstParametrosEntrada.add(eGarantia.getFechaVariable2());
			lstParametrosEntrada.add(eGarantia.getFechaVariable3());
			lstParametrosEntrada.add(eGarantia.getFechaVariable4());
			lstParametrosEntrada.add(eGarantia.getFechaComercial());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());
			lstParametrosEntrada.add(eGarantia.getMontoOriginalGarantia());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoValoracion());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getMontoVariable1());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getMontoVariable4());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getDepositario());
			lstParametrosEntrada.add(eGarantia.getAlmacen());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getClase());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getModelo());
			lstParametrosEntrada.add(eGarantia.getCarroceria());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable2());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable4());
			lstParametrosEntrada.add(eGarantia.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantia.getBancoEmisor());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getUbicacion2());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getNumeroEndoso());
			lstParametrosEntrada.add(eGarantia.getMontoDisponible());
			lstParametrosEntrada.add(eGarantia.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantia.getTipocambio());
			lstParametrosEntrada.add(eGarantia.getPorcentajeDisponible());
			lstParametrosEntrada.add(eGarantia.getCodigoAsignacionInmueble());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIAMANTENIMIENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			//lstParametrosEntrada.add(indicadorTipoGarantia);
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoEstadoGarantiaSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getObservacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getFechaComercial());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoComercial());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoTasacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoGravamen());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoValorRealizacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getPorcentajeAsignado());
			lstParametrosEntrada.add(eGarantiaSolicitud.getPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCorrelativoPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getTipoPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getFechaVencimientoPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getValorPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantiaSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eGarantiaSolicitud.getUsuarioRegistro().getNombreUsuarioSIAF());
		
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIASOLICITUD, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitudSIAF(EGarantiaSolicitud eGarantiaSolicitud,EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoTipoGarantiaReal());
			//Para Vehiculo
			lstParametrosEntrada.add(eGarantiaSolicitud.getPlaca());
			lstParametrosEntrada.add(eGarantiaSolicitud.getClase());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMarca());
			lstParametrosEntrada.add(eGarantiaSolicitud.getModelo());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCarroceria());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMotor());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSerie());
			lstParametrosEntrada.add(eGarantiaSolicitud.getColor());
			lstParametrosEntrada.add(eGarantiaSolicitud.getDescripcionCombustible());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoClase());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoMarca());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoModelo());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoCombustible());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoNivelRiesgo());
			lstParametrosEntrada.add(eGarantiaSolicitud.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getIndicadorNuevo());
			//Para Maquinaria
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getMarca());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getModelo());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getSerie());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getMotor());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCantidad());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCodigoTipoPrenda());
			//Para Predios
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getDireccion());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getAreaTerreno());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getAreaConstruida());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getNumeroPisos());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getUsoPredio());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCodigoTipoPrenda2());
		
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIASOLICITUDSIAF, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar. Solicitud Garantia SIAF", objEx);
		}
		return mensaje;
	}
	
	
	public List<EGarantiaSolicitud> listarGarantiaPorLiberar(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAPORLIBERAR, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					//oEGarantia.setCodigoServicioGarantia(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantia.setDescripcionCreditoRelacionado(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCOPE")));
					oEGarantia.setCodigoEstadoLevantamiento(oResultSet.getInt("CODESTSOL"));
					oEGarantia.setDescripcionEstadoLevantamiento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTSOL")));
					oEGarantia.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaLiberada(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIALIBERADA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEGarantia.setCodigoServicioGarantia(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESGAR"));
					oEGarantia.setCodigoEstadoLevantamiento(oResultSet.getInt("CODESTSOL"));
					oEGarantia.setDescripcionEstadoLevantamiento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTSOL")));
					oEGarantia.setCodigoEstadoDocumento(oResultSet.getInt("CODESTDOC"));
					oEGarantia.setDescripcionEstadoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTDOC")));
					oEGarantia.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	
	public List<EOperacionDocumento> listarSolicitudDocumentoGarantia(int codigo, String descripcion,EGarantia eOGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOGarantia.getTipoDocumento());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionDocumento.setCodigoSolicitudCredito(oResultSet.getInt("SOLCRE"));
					oEOperacionDocumento.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionDocumento.setNombreCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionDocumento.setCodigoGarantia(oResultSet.getInt("GARANT"));
					//oEOperacionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					//oEOperacionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					//oEOperacionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					//oEOperacionDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionDocumento.setCodigoAreaEmisora(oResultSet.getInt("AREAEMI"));
					oEOperacionDocumento.setCodigoAreaReceptora(oResultSet.getInt("AREAREV"));
					//oEOperacionDocumento.setDescripcionDocumento(oResultSet.getString("DESCRI"));
					//oEOperacionDocumento.setFirmaDocumento(oResultSet.getString("FIRMA"));
					oEOperacionDocumento.setEstadoDocumento(oResultSet.getInt("ESTSOL"));
					oEOperacionDocumento.setEstadoDocumentoLegal(oResultSet.getInt("ESTLEG"));
					oEOperacionDocumento.setEstadoDocumentoNegocio(oResultSet.getInt("ESTNEG"));
					oEOperacionDocumento.setEstadoDocumentoNotaria(oResultSet.getInt("ESTNOT"));
					oEOperacionDocumento.setDescripcionEstadoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTSOL")));
					oEOperacionDocumento.setDescripcionEstadoDocumentoLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTLEG")));
					oEOperacionDocumento.setDescripcionEstadoDocumentoNegocio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTNEG")));
					oEOperacionDocumento.setDescripcionEstadoDocumentoNotaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTNOT")));
					//oEOperacionDocumento.setObervacionDocumento(oResultSet.getString("OBSERV"));
					oEOperacionDocumento.setUsuarioRegist(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEOperacionDocumento.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setUsuarioModif(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUMOD")));
					oEOperacionDocumento.setFechaModificacion(oResultSet.getDate("FECMOD"));
					oEOperacionDocumento.setHoraModificacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setTipoDocumento(oResultSet.getInt("TIPDOC"));
					oEOperacionDocumento.setIndicadorConfirmarNegocios(oResultSet.getInt("INDCONFIRMNEG"));
					
					
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	public List<EOperacionDocumento> listarSolicitudDocumentoXNroGarantia(int codigo, String descripcion,EGarantia eOGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOGarantia.getTipoDocumento());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOXNROGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionDocumento.setCodigoSolicitudCredito(oResultSet.getInt("SOLCRE"));
					oEOperacionDocumento.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionDocumento.setNombreCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionDocumento.setCodigoGarantia(oResultSet.getInt("GARANT"));
					//oEOperacionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					//oEOperacionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					//oEOperacionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					//oEOperacionDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionDocumento.setCodigoAreaEmisora(oResultSet.getInt("AREAEMI"));
					oEOperacionDocumento.setCodigoAreaReceptora(oResultSet.getInt("AREAREV"));
					//oEOperacionDocumento.setDescripcionDocumento(oResultSet.getString("DESCRI"));
					//oEOperacionDocumento.setFirmaDocumento(oResultSet.getString("FIRMA"));
					oEOperacionDocumento.setEstadoDocumento(oResultSet.getInt("ESTSOL"));
					oEOperacionDocumento.setEstadoDocumentoLegal(oResultSet.getInt("ESTLEG"));
					oEOperacionDocumento.setEstadoDocumentoNegocio(oResultSet.getInt("ESTNEG"));
					oEOperacionDocumento.setEstadoDocumentoNotaria(oResultSet.getInt("ESTNOT"));
					oEOperacionDocumento.setDescripcionEstadoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTSOL")));
					oEOperacionDocumento.setDescripcionEstadoDocumentoLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTLEG")));
					oEOperacionDocumento.setDescripcionEstadoDocumentoNegocio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTNEG")));
					oEOperacionDocumento.setDescripcionEstadoDocumentoNotaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTNOT")));
					//oEOperacionDocumento.setObervacionDocumento(oResultSet.getString("OBSERV"));
					oEOperacionDocumento.setUsuarioRegist(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEOperacionDocumento.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setUsuarioModif(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUMOD")));
					oEOperacionDocumento.setFechaModificacion(oResultSet.getDate("FECMOD"));
					oEOperacionDocumento.setHoraModificacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setTipoDocumento(oResultSet.getInt("TIPDOC"));
					oEOperacionDocumento.setIndicadorConfirmarFirmaDocumentoLegal(oResultSet.getInt("INDCONFIRMAR"));
					oEOperacionDocumento.setIndicadorConfirmarFirmaNotaria(oResultSet.getInt("INDCONFIRMARNOT"));
					oEOperacionDocumento.setIndicadorSolicitudFirmaDocumento(oResultSet.getInt("INDSOLICFIRMA"));
					
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	
	
	public List<EOperacionDocumento> listarSolicitudDocumentoGarantiaDetalle(EOperacionDocumento eOperacionDocumento) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionDocumento.getTipoDocumento());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOLICITUDDOCUMENTOGARANTIADETALLE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionDocumento.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEOperacionDocumento.setCodigoDocumento(oResultSet.getInt("SECUEN"));
					oEOperacionDocumento.setTipoDocumento(oResultSet.getInt("TIPDOC"));
					oEOperacionDocumento.setCodigoDocumentoLaserFiche(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODDOCLSF")));
					oEOperacionDocumento.setNombreDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMDOC")));
					oEOperacionDocumento.setNombreDocumentoLaserFiche(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMDOCLSF")));
					oEOperacionDocumento.setNombreDocumentoOriginal(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMDOCORI")));
					oEOperacionDocumento.setCodigoAreaEmisora(oResultSet.getInt("AREAEMI"));
					oEOperacionDocumento.setFirmaDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("FIRMA")));
					oEOperacionDocumento.setObervacionDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSERV")));
					oEOperacionDocumento.setUsuarioRegist(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEOperacionDocumento.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setUsuarioModif(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUMOD")));
					oEOperacionDocumento.setFechaModificacion(oResultSet.getDate("FECMOD"));
					oEOperacionDocumento.setHoraModificacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setValidarObservacion(oResultSet.getString("USUOBS").isEmpty() && oResultSet.getInt("AREOBS")==0 ? false : true);
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	public List<EOperacionDocumento> listarSolicitudDesembolsoGarantia(int codigo, String descripcion,EGarantia eOGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOGarantia.getTipoDocumento());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACIONDESEMBOLSOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionDocumento.setCodigoSolicitudCredito(oResultSet.getInt("SOLCRE"));
					oEOperacionDocumento.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionDocumento.setNombreCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionDocumento.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEOperacionDocumento.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEOperacionDocumento.setCodigoTipoPredios(oResultSet.getInt("TIPPRE"));
					oEOperacionDocumento.setCodigoAreaEmisora(oResultSet.getInt("AREAEMI"));
					oEOperacionDocumento.setCodigoAreaReceptora(oResultSet.getInt("AREAREV"));
					oEOperacionDocumento.setEstadoDesembolso(oResultSet.getInt("ESTDES"));
					oEOperacionDocumento.setDescripcionEstadoDesembolso(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTDES")));
					oEOperacionDocumento.setUsuarioRegist(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEOperacionDocumento.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setUsuarioModif(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUMOD")));
					oEOperacionDocumento.setFechaModificacion(oResultSet.getDate("FECMOD"));
					oEOperacionDocumento.setHoraModificacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setTipoDocumento(oResultSet.getInt("TIPDOC"));
					
					
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	public List<EOperacionDocumento> listarSolicitudDesembolsoXNroGarantia(int codigo, String descripcion,EGarantia eOGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOGarantia.getTipoDocumento());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACIONDESEMBOLSOXNROGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionDocumento.setCodigoSolicitudCredito(oResultSet.getInt("SOLCRE"));
					oEOperacionDocumento.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionDocumento.setNombreCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionDocumento.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEOperacionDocumento.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEOperacionDocumento.setCodigoTipoPredios(oResultSet.getInt("TIPPRE"));
					oEOperacionDocumento.setCodigoAreaEmisora(oResultSet.getInt("AREAEMI"));
					oEOperacionDocumento.setCodigoAreaReceptora(oResultSet.getInt("AREAREV"));
					oEOperacionDocumento.setEstadoDesembolso(oResultSet.getInt("ESTDES"));
					oEOperacionDocumento.setDescripcionEstadoDesembolso(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTDES")));
					oEOperacionDocumento.setUsuarioRegist(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEOperacionDocumento.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setUsuarioModif(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUMOD")));
					//oEOperacionDocumento.setFechaModificacion(oResultSet.getDate("FECMOD"));
					//oEOperacionDocumento.setHoraModificacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setTipoDocumento(oResultSet.getInt("TIPDOC"));
					oEOperacionDocumento.setIndicadorConfirmarBloqueoRegistral(oResultSet.getInt("INDCONFBLOQUEO"));
					oEOperacionDocumento.setIndicadorConfirmacionDesembolso(oResultSet.getInt("INDCONFDESEMB"));
					oEOperacionDocumento.setIndicadorConfirmarCartaFianza(oResultSet.getInt("INDCONFIANZA"));
					
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	public List<EOperacionSolicitud> listarSolicitudTramiteOperativoLegal(int codigo, String descripcion,EUsuario eUsuario) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEOperacionSolicitud= null;
		List<EOperacionSolicitud> lstOperacionSolicitud = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eUsuario.getCodigoArea());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_TRAMITEOPERATIVOSOLICITUDLEGAL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionSolicitud = new ArrayList<EOperacionSolicitud>();
				while (oResultSet.next()) {
					oEOperacionSolicitud = new EOperacionSolicitud();
					oEOperacionSolicitud.setCodigoClientePersona(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitud.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitud.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEOperacionSolicitud.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEOperacionSolicitud.setFechaRevision(oResultSet.getDate("FECSOL"));
					oEOperacionSolicitud.setNombrePersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEOperacionSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEOperacionSolicitud.setCodigoStatus(oResultSet.getInt("STATUS"));
					oEOperacionSolicitud.setAbreviacionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIP")));
					oEOperacionSolicitud.setDescripcionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIPLARGO")));
					oEOperacionSolicitud.setDescripcionStatus(UFuncionesGenerales.revisaCadena(oResultSet.getString("ADESEM")));	
					oEOperacionSolicitud.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEOperacionSolicitud.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEOperacionSolicitud.setMontoAprobado(oResultSet.getDouble("MTOAPR"));
					oEOperacionSolicitud.setCodigoMoneda(oResultSet.getInt("MONSOL"));
					lstOperacionSolicitud.add(oEOperacionSolicitud);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionSolicitud;
	}
	
	public List<EGarantia> listarInmueblesGarantiaPredios(long codigoGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		List<EGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_INMUEBLESGARANTIAPREDIOS, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantia>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setNumeroSecuenciaInmueble(oResultSet.getInt("NROGAR"));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					oEGarantia.setDescripcionA(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRI")));
					oEGarantia.setCodigoUbigeo(oResultSet.getInt("UBIGEO"));
					oEGarantia.setFechaGravamen(oResultSet.getDate("FECGRAV"));	
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));	
					oEGarantia.setMontoEjecucion(oResultSet.getDouble("MTOEJE"));			
					oEGarantia.setMontoVariable2(oResultSet.getDouble("MONTO2"));
					oEGarantia.setMontoVariable3(oResultSet.getDouble("MONTO3"));
					oEGarantia.setCodigoTipoBien(oResultSet.getInt("TIPBIE"));				
					oEGarantia.setNumeroVariable1(oResultSet.getInt("NUMER1"));
					oEGarantia.setPartidaRegistral(UFuncionesGenerales.revisaCadena(oResultSet.getString("PARREG")));
					oEGarantia.setOficinaRegistral(oResultSet.getInt("OFIREG"));
					oEGarantia.setTipoRegistral(oResultSet.getInt("TIPREG"));
					oEGarantia.setMontoComercial(oResultSet.getDouble("MONCOM"));
					oEGarantia.setDescripcionOficinaRegistral(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESOFIREG")));
					oEGarantia.setDescripcionTipoRegistral(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIPREG")));
						


					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	
	public List<EOperacionSolicitud> listarDetalleFlagsSolicitudCredito(long codigoSolicitudCredito) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEFlags= null;
		List<EOperacionSolicitud> lstFlags = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitudCredito);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_DETALLEFLAGSSOLICITUDCREDITO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstFlags = new ArrayList<EOperacionSolicitud>();
				while (oResultSet.next()) {
					oEFlags = new EOperacionSolicitud();
					oEFlags.setCodigoSolicitudCredito(oResultSet.getInt("NROSOL"));
					oEFlags.setTipoFlag(oResultSet.getInt("TIPFLG"));
					oEFlags.setNumeroFlag(oResultSet.getInt("NUMFLG"));
					oEFlags.setNombreFlag(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMFLG")));
					oEFlags.setDescripcionFlag(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESFLG")));
					oEFlags.setValorFlag(oResultSet.getInt("FLGFLG"));
					oEFlags.setActualizacionFlag(oResultSet.getInt("ACTFLG"));
					oEFlags.setDescripcionValorFlag(UFuncionesGenerales.revisaCadena(oResultSet.getString("DETIPO")));
					oEFlags.setDescripcionActualizacionFlag(UFuncionesGenerales.revisaCadena(oResultSet.getString("DSTATU")));
					lstFlags.add(oEFlags);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstFlags;
	}
	
	public List<EFirmanteSolicitud> listarFirmantesDocumentoSolicitud(long codigoSolicitudCredito) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EFirmanteSolicitud oEFirmanteSolicitud= null;
		List<EFirmanteSolicitud> lstFirmanteSolicitud = null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitudCredito);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_FIRMANTESDOCUMENTOSOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstFirmanteSolicitud = new ArrayList<EFirmanteSolicitud>();
				while (oResultSet.next()) {
					oEFirmanteSolicitud = new EFirmanteSolicitud();
					oEFirmanteSolicitud.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEFirmanteSolicitud.setCodigoTipoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("TPOFRM")));
					oEFirmanteSolicitud.setSecuenciaFirmante(oResultSet.getInt("SECFRM"));
					oEFirmanteSolicitud.setCodigoSocioFirmante(oResultSet.getInt("CODFRM"));
					oEFirmanteSolicitud.setCodigoRepresentanteFirmante(oResultSet.getInt("REPFRM"));
					oEFirmanteSolicitud.setNombreFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMFRM")));
					oEFirmanteSolicitud.setDireccionFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRFRM")));
					oEFirmanteSolicitud.setCodigoUbigeoFirmante(oResultSet.getInt("CIUFRM"));
					oEFirmanteSolicitud.setTipoDocumentoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOFRM")));
					oEFirmanteSolicitud.setNumeroDocumentoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NDOFRM")));
					oEFirmanteSolicitud.setInscripcionPoderesFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("INPFRM")));
					oEFirmanteSolicitud.setFirmaConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("FIRCFR")));
					oEFirmanteSolicitud.setCodigoConyugue(oResultSet.getInt("CODCFR"));
					oEFirmanteSolicitud.setDireccionConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRCFR")));
					oEFirmanteSolicitud.setCodigoUbigeoConyugue(oResultSet.getInt("CIUCFR"));
					oEFirmanteSolicitud.setNombreConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCFR")));
					oEFirmanteSolicitud.setTipoDocumentoIDConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCFR")));
					oEFirmanteSolicitud.setNumeroDocumentoIDConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NDOCFR")));
					oEFirmanteSolicitud.setInscripcionPoderesConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("INPCFR")));
					oEFirmanteSolicitud.setOkFirma(UFuncionesGenerales.revisaCadena(oResultSet.getString("OOKFRM")));
					oEFirmanteSolicitud.setSiFirma(UFuncionesGenerales.revisaCadena(oResultSet.getString("SIFIRM")));
					oEFirmanteSolicitud.setDescripcionTipoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESFRM")));
					oEFirmanteSolicitud.setDescripcionTipoFirmanteLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESLARFRM")));
					oEFirmanteSolicitud.setFechaFirmaSocio(oResultSet.getDate("FECFFRM"));
					oEFirmanteSolicitud.setHoraReg(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFFRM")));
					oEFirmanteSolicitud.setHoraRegistroFirmaSocio(UFuncionesGenerales.convertirCadenaAFecha(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFFRM")),"HH:mm:ss"));
					oEFirmanteSolicitud.setHoraRegCony(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFCFR")));
					oEFirmanteSolicitud.setHoraRegistroFirmaConyugue(UFuncionesGenerales.convertirCadenaAFecha(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFCFR")),"HH:mm:ss"));
					oEFirmanteSolicitud.setFlagFirmado(oResultSet.getInt("FLGFRM"));
					oEFirmanteSolicitud.setFlagFirmadoConyugue(oResultSet.getInt("FLGCFR"));
					oEFirmanteSolicitud.setFechaFirmaConyugue(oResultSet.getDate("FECFCFR"));
					lstFirmanteSolicitud.add(oEFirmanteSolicitud);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstFirmanteSolicitud;
	}
	
	
	public List<EGarantiaSolicitud> listarGarantiaAsociadaSolicitudFirmante(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOLICITUDGARANTIAASOCIADA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantiaSolicitud = new EGarantiaSolicitud();
					oEGarantiaSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaSolicitud.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEGarantiaSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantiaSolicitud.setDescripcionGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantiaSolicitud.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantiaSolicitud.setCodigoMonedaGarantia(oResultSet.getInt("MONGAR"));
					oEGarantiaSolicitud.setNumeroGarantiaReal(oResultSet.getInt("GARGAR"));
					oEGarantiaSolicitud.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantiaSolicitud.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCGARTIP")));
					oEGarantiaSolicitud.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					oEGarantiaSolicitud.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantiaSolicitud.setDescripcionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantiaSolicitud.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantiaSolicitud.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantiaSolicitud.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantiaSolicitud.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantiaSolicitud.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantiaSolicitud.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantiaSolicitud.setMotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("NMOTOR")));
					oEGarantiaSolicitud.setSerie(UFuncionesGenerales.revisaCadena(oResultSet.getString("CHASIS")));
					oEGarantiaSolicitud.setColor(UFuncionesGenerales.revisaCadena(oResultSet.getString("COLORV")));
					oEGarantiaSolicitud.setCodigoClase(oResultSet.getInt("CCLASE"));
					oEGarantiaSolicitud.setCodigoMarca(oResultSet.getInt("CMARCA"));
					oEGarantiaSolicitud.setCodigoModelo(oResultSet.getInt("CMODEL"));
					oEGarantiaSolicitud.setCodigoCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("CCOMBU")));
					oEGarantiaSolicitud.setDescripcionCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("COMBUS")));
					oEGarantiaSolicitud.setCodigoNivelRiesgo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRIESG")));
					oEGarantiaSolicitud.setAnioFabricacion(oResultSet.getInt("AÑOFAB"));
					oEGarantiaSolicitud.setMontoValorizacion(oResultSet.getDouble("VALORI"));
					oEGarantiaSolicitud.setIndicadorNuevo(UFuncionesGenerales.revisaCadena(oResultSet.getString("VNUEVO")));
					lstGarantia.add(oEGarantiaSolicitud);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	
	public List<EComprobanteGarantia> listarComprobanteGarantiaSolicitud(long codigoSolicitud,int secuenciaGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EComprobanteGarantia oEComprobanteGarantia= null;
		List<EComprobanteGarantia> lstComprobanteGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			lstParametrosEntrada.add(secuenciaGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_COMPROBANTESGARANTIAASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstComprobanteGarantia = new ArrayList<EComprobanteGarantia>();
				while (oResultSet.next()) {
					oEComprobanteGarantia = new EComprobanteGarantia();
					oEComprobanteGarantia.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEComprobanteGarantia.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEComprobanteGarantia.setSecuenciaDetalle(oResultSet.getInt("SECDET"));
					oEComprobanteGarantia.setNumeroComprobantePago(UFuncionesGenerales.revisaCadena(oResultSet.getString("NROCPG")));
					oEComprobanteGarantia.setFechaComprobantePago(oResultSet.getDate("FECCOMP"));
					oEComprobanteGarantia.setCodigoMonedaComprobantePago(oResultSet.getInt("MNDCPG"));
					oEComprobanteGarantia.setImporteComprobantePago(oResultSet.getDouble("IMPCPG"));
					oEComprobanteGarantia.setDescripcionComprobantePago(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCPG")));
					oEComprobanteGarantia.setAbreviacionMonedaComprobante(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEComprobanteGarantia.setDescripcionMonedaComprobante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					//oEGarantiaSolicitud.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					//oEGarantiaSolicitud.setDescripcionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));


					lstComprobanteGarantia.add(oEComprobanteGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstComprobanteGarantia;
	}
	
	
	public List<ERepresentanteCIA> listarMaestroRepresentantesCompania() {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERepresentanteCIA oERepresentanteCIA= null;
		List<ERepresentanteCIA> lstRepresentanteCIA = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_MAESTROREPRESENTANTECOMPANIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstRepresentanteCIA = new ArrayList<ERepresentanteCIA>();
				while (oResultSet.next()) {
					oERepresentanteCIA = new ERepresentanteCIA();
					oERepresentanteCIA.setCodigoCompania(oResultSet.getInt("CODCIA"));
					oERepresentanteCIA.setNombreCompania(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCIA")));
					oERepresentanteCIA.setDireccionCompania(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRCIA")));
					oERepresentanteCIA.setCodigoUbigeoCompania(oResultSet.getInt("CIUCIA"));
					oERepresentanteCIA.setRucCompania(oResultSet.getLong("RUCCIA"));
					oERepresentanteCIA.setCodigoRepresentante(oResultSet.getInt("CODREP"));
					oERepresentanteCIA.setNombreRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMACL")));
					oERepresentanteCIA.setNombreCortoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCOR")));
					oERepresentanteCIA.setInscripcionPoderes(UFuncionesGenerales.revisaCadena(oResultSet.getString("INSREP")));
					oERepresentanteCIA.setNominativoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DENREP")));
					oERepresentanteCIA.setTipoDocumentoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOREP")));
					oERepresentanteCIA.setNumeroDocumentoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NDOREP")));
					oERepresentanteCIA.setFichaInscripcionPoderes(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICREP")));
					oERepresentanteCIA.setRegistroPoderes(UFuncionesGenerales.revisaCadena(oResultSet.getString("REGREP")));
					oERepresentanteCIA.setCargoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("CRGREP")));
					oERepresentanteCIA.setNacionalidadRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NACREP")));
					oERepresentanteCIA.setPoderesRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("PODREP")));
					oERepresentanteCIA.setCodigoPiePagina(oResultSet.getInt("PIEPAG"));


					lstRepresentanteCIA.add(oERepresentanteCIA);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRepresentanteCIA;
	}
	
	public List<ERepresentanteCIAContrato> listarRepresentantesCompaniaxContrato(int tipoContrato,long numeroContrato) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERepresentanteCIAContrato oERepresentanteCIAContrato= null;
		List<ERepresentanteCIAContrato> lstRepresentanteCIAContrato = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(tipoContrato);
			lstParametrosEntrada.add(numeroContrato);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_REPRESENTANTECOMPANIACONTRATO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstRepresentanteCIAContrato = new ArrayList<ERepresentanteCIAContrato>();
				while (oResultSet.next()) {
					oERepresentanteCIAContrato = new ERepresentanteCIAContrato();
					oERepresentanteCIAContrato.setNumeroContrato(oResultSet.getInt("NUMERO"));
					oERepresentanteCIAContrato.setCodigoTipoContrato(oResultSet.getInt("CONTRA"));
					oERepresentanteCIAContrato.setCodigoMonedaContrato(oResultSet.getInt("MONEDA"));
					oERepresentanteCIAContrato.setCodigoRepresentante(oResultSet.getInt("CODREP"));
					oERepresentanteCIAContrato.setNombreRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMREP")));
					oERepresentanteCIAContrato.setCargoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARREP")));
					oERepresentanteCIAContrato.setFirmadoRepresentante(oResultSet.getInt("FIRREP"));
					oERepresentanteCIAContrato.setFechaFirmadoRepresentante(oResultSet.getDate("FECFIR"));
					oERepresentanteCIAContrato.setHoraFirmado(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORFIR")));
					oERepresentanteCIAContrato.setUsuarioFirmado(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRFIR")));

					lstRepresentanteCIAContrato.add(oERepresentanteCIAContrato);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRepresentanteCIAContrato;
	}
	
	public List<EFirmanteSolicitud> listarDetalleFirmantesContratoGarantia(EFirmanteSolicitud eFirmanteContrato) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EFirmanteSolicitud oEFirmanteContrato= null;
		List<EFirmanteSolicitud> lstFirmanteContrato = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eFirmanteContrato.getTipoPlantilla());
			lstParametrosEntrada.add(eFirmanteContrato.getCodigoMonedaPlantilla());
			lstParametrosEntrada.add(eFirmanteContrato.getTipoVariante());
			lstParametrosEntrada.add(eFirmanteContrato.getNumeroDocumento());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_DETALLEFIRMANTESCONTRATO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstFirmanteContrato = new ArrayList<EFirmanteSolicitud>();
				while (oResultSet.next()) {
					oEFirmanteContrato = new EFirmanteSolicitud();
					oEFirmanteContrato.setCodigoTipoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("TPOFRM")));
					oEFirmanteContrato.setSecuenciaFirmante(oResultSet.getInt("SECFRM"));
					oEFirmanteContrato.setCodigoSocioFirmante(oResultSet.getInt("CODFRM"));
					oEFirmanteContrato.setCodigoRepresentanteFirmante(oResultSet.getInt("REPFRM"));
					oEFirmanteContrato.setNombreFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMFRM")));
					oEFirmanteContrato.setDireccionFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRFRM")));
					oEFirmanteContrato.setCodigoUbigeoFirmante(oResultSet.getInt("CIUFRM"));
					oEFirmanteContrato.setDistritoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DISFRM")));
					oEFirmanteContrato.setProvinciaFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("PRVFRM")));
					oEFirmanteContrato.setDepartamentoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DPTFRM")));
					oEFirmanteContrato.setTipoDocumentoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOFRM")));
					oEFirmanteContrato.setDescripcionTipoDocIdentidadFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DDOFRMI")));
					oEFirmanteContrato.setNumeroDocumentoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NDOFRM")));
					oEFirmanteContrato.setInscripcionPoderesFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("INPFRM")));
					oEFirmanteContrato.setFirmaConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("FIRCFR")));
					oEFirmanteContrato.setCodigoConyugue(oResultSet.getInt("CODCFR"));
					oEFirmanteContrato.setDireccionConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRCFR")));
					oEFirmanteContrato.setDescripcionTipoDocumentoIDConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DDOFCFRI")));
					oEFirmanteContrato.setCodigoUbigeoConyugue(oResultSet.getInt("CIUCFR"));
					oEFirmanteContrato.setNombreConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCFR")));
					oEFirmanteContrato.setTipoDocumentoIDConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCFR")));
					oEFirmanteContrato.setNumeroDocumentoIDConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NDOCFR")));
					oEFirmanteContrato.setInscripcionPoderesConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("INPCFR")));
					
					oEFirmanteContrato.setFechaFirmaSocio(oResultSet.getDate("FECFFRM"));
					oEFirmanteContrato.setHoraReg(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFFRM")));
					//oEFirmanteContrato.setHoraRegistroFirmaSocio(UFuncionesGenerales.convertirCadenaAFecha(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFFRM")),"HH:mm:ss"));
					oEFirmanteContrato.setHoraRegCony(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFCFR")));
					//oEFirmanteContrato.setHoraRegistroFirmaConyugue(UFuncionesGenerales.convertirCadenaAFecha(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOFCFR")),"HH:mm:ss"));
					oEFirmanteContrato.setFlagFirmado(oResultSet.getInt("FLGFRM"));
					oEFirmanteContrato.setFlagFirmadoConyugue(oResultSet.getInt("FLGCFR"));
					oEFirmanteContrato.setFechaFirmaConyugue(oResultSet.getDate("FECFCFR"));
					oEFirmanteContrato.setDescripcionTipoFirmante(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESFRM")));
					oEFirmanteContrato.setDescripcionTipoFirmanteLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESLARFRM")));
					lstFirmanteContrato.add(oEFirmanteContrato);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstFirmanteContrato;
	}
	
	public List<EOperacionSolicitud> listarObservacionSolicitudTramiteOperativoLegal(EOperacionSolicitud eOperacionSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEOperacionSolicitud= null;
		EUsuario oEUsuario = null;
		List<EOperacionSolicitud> lstOperacionSolicitud = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());	 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OBSERVACIONTRAMITEOPERATIVOSOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionSolicitud = new ArrayList<EOperacionSolicitud>();
				while (oResultSet.next()) {
					oEOperacionSolicitud = new EOperacionSolicitud();
					
					oEOperacionSolicitud.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitud.setSecuenciaSolicitud(oResultSet.getInt("SECUEN"));
					oEOperacionSolicitud.setCodigoClientePersona(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitud.setNombrePersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEOperacionSolicitud.setCodigoMoneda(oResultSet.getInt("MONEDA"));				
					oEOperacionSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEOperacionSolicitud.setObservacionConformidad(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSERV")));
					
					oEUsuario = new EUsuario();
					oEUsuario.setNombreUsuario(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
					oEOperacionSolicitud.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionSolicitud.setHoraRegistroObservacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					lstOperacionSolicitud.add(oEOperacionSolicitud);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionSolicitud;
	}
	
	public List<EFlagReqLegal> listarDetalleFlagRequisitoLegal(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EFlagReqLegal oEFlagRequisitoLegal= null;
		EUsuario oEUsuario = null;
		List<EFlagReqLegal> lstFlagRequisitoLegal = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);	 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_DETALLEFLAGREQUISITOLEGAL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstFlagRequisitoLegal = new ArrayList<EFlagReqLegal>();
				while (oResultSet.next()) {
					oEFlagRequisitoLegal = new EFlagReqLegal();
					oEFlagRequisitoLegal.setNumeroSolicitud(oResultSet.getLong("NROSOL"));
					oEFlagRequisitoLegal.setNumeroFlag(oResultSet.getInt("NUMFLG"));
					oEFlagRequisitoLegal.setDescripcionFlag(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESFLG")));
					oEFlagRequisitoLegal.setModoIngresoFlag(oResultSet.getInt("MODFLG"));
					oEFlagRequisitoLegal.setActualizacionFlag(oResultSet.getInt("ACTFLG"));
					
					oEUsuario = new EUsuario();
					oEUsuario.setNombreUsuario(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEFlagRequisitoLegal.setUsuarioRegistro(oEUsuario);
					oEFlagRequisitoLegal.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEFlagRequisitoLegal.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEFlagRequisitoLegal.setDescripcionModoIngresoFlag(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESMODFLG")));
					oEFlagRequisitoLegal.setDescripcionActualizacionFlag(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESACTFLG")));
					lstFlagRequisitoLegal.add(oEFlagRequisitoLegal);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstFlagRequisitoLegal;
	}
		
	public EMensaje eliminarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getSecuenciaDocumento());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_DETALLESOLICITUDDOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarInmuebleGarantiaPredios(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getNumeroSecuenciaInmueble());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_INMUEBLESGARANTIAPREDIOS, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje eliminarRepresentanteCompaniaContratoF7433(ERepresentanteCIAContrato eRepresentanteCiaContrato) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getCodigoTipoContrato());
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getNumeroContrato());
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getCodigoRepresentante());
			lstParametrosEntrada.add(eRepresentanteCiaContrato.getNombreRepresentante());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_REPRESENTANTECIAXCONTRATO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eFlagRequisitoLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eFlagRequisitoLegal.getNumeroFlag());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_DETALLEFLAGREQUISITOLEGAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<EGarantiaTramite> listarHistoricoTramiteGarantia(long codigoGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite= null;
		EUsuario oEUsuario = null;
		List<EGarantiaTramite> lstGarantia = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_HISTORICOGARANTIATRAMITE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaTramite>();
				while (oResultSet.next()) {
					oEGarantiaTramite = new EGarantiaTramite();
					oEGarantiaTramite.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantiaTramite.setTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantiaTramite.setSecuenciaHistorica(oResultSet.getInt("SECUEN"));
					oEUsuario = new EUsuario();
					oEUsuario.setNombreUsuarioSIAF(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEGarantiaTramite.setUsuarioRegistro(oEUsuario);
					oEGarantiaTramite.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEGarantiaTramite.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					
					oEGarantiaTramite.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantiaTramite.setNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL"));	
					oEGarantiaTramite.setFechaIngreso(oResultSet.getDate("FECINL"));
					oEGarantiaTramite.setEvaluacionDocumento(oResultSet.getInt("EVADOC"));
					oEGarantiaTramite.setFechaElaboracionContrato(oResultSet.getDate("FECELC"));
					oEGarantiaTramite.setUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")));
					oEGarantiaTramite.setFechaFirmaContrato(oResultSet.getDate("FECFIC"));
					oEGarantiaTramite.setFechaIngresoNotaria(oResultSet.getDate("FECFIN"));
					oEGarantiaTramite.setFechaLegalizacionFirma(oResultSet.getDate("FECLEF"));
					oEGarantiaTramite.setNumeroKardex(oResultSet.getInt("KARDEN"));
					oEGarantiaTramite.setCodigoNotario(oResultSet.getInt("NOTARI"));
					oEGarantiaTramite.setFechaIngresoRegistroB(oResultSet.getDate("FECBINR"));
					oEGarantiaTramite.setFechaVigenciaAsientoB(oResultSet.getDate("FECBVAP"));
					oEGarantiaTramite.setDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")));
					oEGarantiaTramite.setDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")));
					oEGarantiaTramite.setFechaObservacionB(oResultSet.getDate("FECBOBS"));
					oEGarantiaTramite.setFechaTachaB(oResultSet.getDate("FECBTAC"));
					oEGarantiaTramite.setFechaInscripcionB(oResultSet.getDate("FECBINS"));
					oEGarantiaTramite.setFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")));
					oEGarantiaTramite.setTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")));
					oEGarantiaTramite.setCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")));
					oEGarantiaTramite.setTituloB(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULB")));
					
					oEGarantiaTramite.setFechaIngresoRegistro(oResultSet.getDate("FECINR"));
					oEGarantiaTramite.setFechaVigenciaAsiento(oResultSet.getDate("FECVAP"));
					oEGarantiaTramite.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")));
					oEGarantiaTramite.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")));				
					oEGarantiaTramite.setFechaObservacion(oResultSet.getDate("FECOBS"));
					oEGarantiaTramite.setFechaTacha(oResultSet.getDate("FECTAC"));
					oEGarantiaTramite.setFechaInscripcion(oResultSet.getDate("FECINS"));
					oEGarantiaTramite.setFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")));
					oEGarantiaTramite.setTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")));
					oEGarantiaTramite.setCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")));
					oEGarantiaTramite.setTituloA(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULA")));
					
					oEGarantiaTramite.setDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOTARIO")));
					//Validacion para Historico Antes/Despues
					oEGarantiaTramite.setValidarNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL") == oResultSet.getInt("@HOJINL") ? false : true);
					oEGarantiaTramite.setValidarFechaIngreso(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECINL")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECINL")))  ? false: true);				
					oEGarantiaTramite.setValidarEvaluacionDocumento(oResultSet.getInt("EVADOC") == oResultSet.getInt("@EVADOC") ? false: true);
					oEGarantiaTramite.setValidarFechaElaboracionContrato(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECELC")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECELC"))) ? false: true);
					oEGarantiaTramite.setValidarUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@USRELC"))) ? false : true);
					oEGarantiaTramite.setValidarFechaFirmaContrato(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECFIC")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECFIC"))) ? false: true);
					oEGarantiaTramite.setValidarFechaIngresoNotaria(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECFIN")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECFIN"))) ? false: true);
					oEGarantiaTramite.setValidarFechaLegalizacionFirma(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECLEF")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECLEF"))) ? false: true);
					oEGarantiaTramite.setValidarNumeroKardex(oResultSet.getInt("KARDEN") == oResultSet.getInt("@KARDEN") ? false : true);
					oEGarantiaTramite.setValidarCodigoNotario(oResultSet.getInt("NOTARI") == oResultSet.getInt("@NOTARI") ? false : true);
					oEGarantiaTramite.setValidarFechaIngresoRegistro(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECINR")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECINR"))) ? false: true);
					oEGarantiaTramite.setValidarFechaVigenciaAsiento(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECVAP")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECVAP"))) ? false: true);
					oEGarantiaTramite.setValidarObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")).equals(
							                                 UFuncionesGenerales.revisaCadena(oResultSet.getString("@OBSER1"))) ? false : true);
					oEGarantiaTramite.setValidarObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")).equals(
                            UFuncionesGenerales.revisaCadena(oResultSet.getString("@OBSER2"))) ? false : true);
					oEGarantiaTramite.setValidarFechaObservacion(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECOBS")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECOBS"))) ? false: true);
					oEGarantiaTramite.setValidarFechaTacha(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECTAC")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECTAC"))) ? false: true);
					oEGarantiaTramite.setValidarFechaInscripcion(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECINS")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECINS"))) ? false: true);
					oEGarantiaTramite.setValidarFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@FICINS"))) ? false : true);
					oEGarantiaTramite.setValidarTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TOMINS"))) ? false : true);
					oEGarantiaTramite.setValidarCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@CIUINS"))) ? false : true);
					oEGarantiaTramite.setValidarFechaIngresoRegistroB(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECBINR")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECBINR"))) ? false: true);
					oEGarantiaTramite.setValidarFechaVigenciaAsientoB(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECBVAP")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECBVAP"))) ? false: true);
					oEGarantiaTramite.setValidarDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")).equals(
                            UFuncionesGenerales.revisaCadena(oResultSet.getString("@BOBSE1"))) ? false : true);
					oEGarantiaTramite.setValidarDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")).equals(
                            UFuncionesGenerales.revisaCadena(oResultSet.getString("@BOBSE2"))) ? false : true);
					oEGarantiaTramite.setValidarFechaObservacionB(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECBOBS")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECBOBS"))) ? false: true);
					oEGarantiaTramite.setValidarFechaTachaB(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECBTAC")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECBTAC"))) ? false: true);
					oEGarantiaTramite.setValidarFechaInscripcionB(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECBINS")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECBINS"))) ? false: true);
					oEGarantiaTramite.setValidarFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@BFICIN"))) ? false : true);
					oEGarantiaTramite.setValidarTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@BTOMIN"))) ? false : true);
					oEGarantiaTramite.setValidarCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@BCIUIN"))) ? false : true);
					oEGarantiaTramite.setValidarTituloA(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULA")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TITULA"))) ? false : true);
					oEGarantiaTramite.setValidarTituloB(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULB")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TITULB"))) ? false : true);
					lstGarantia.add(oEGarantiaTramite);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener Historico Tramite Garantia", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaTramite> listarAsientosTramiteGarantia(long codigoGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite= null;
		EUsuario oEUsuario = null;
		List<EGarantiaTramite> lstGarantia = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_ASIENTOTRAMITEGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaTramite>();
				while (oResultSet.next()) {
					oEGarantiaTramite = new EGarantiaTramite();
					oEGarantiaTramite.setNroAsiento(oResultSet.getInt("NASIEN"));
					oEGarantiaTramite.setCodigoGarantia(oResultSet.getInt("GARANT"));
					//oEGarantiaTramite.setTipoGarantia(oResultSet.getInt("TIPGAR"));
					
					oEGarantiaTramite.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantiaTramite.setNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL"));	
					oEGarantiaTramite.setFechaIngreso(oResultSet.getDate("FECINL"));
					oEGarantiaTramite.setEvaluacionDocumento(oResultSet.getInt("EVADOC"));
					oEGarantiaTramite.setFechaElaboracionContrato(oResultSet.getDate("FECELC"));
					oEGarantiaTramite.setUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")));
					oEGarantiaTramite.setFechaFirmaContrato(oResultSet.getDate("FECFIC"));
					oEGarantiaTramite.setFechaIngresoNotaria(oResultSet.getDate("FECFIN"));
					oEGarantiaTramite.setFechaLegalizacionFirma(oResultSet.getDate("FECLEF"));
					oEGarantiaTramite.setNumeroKardex(oResultSet.getInt("KARDEN"));
					oEGarantiaTramite.setCodigoNotario(oResultSet.getInt("NOTARI"));
					oEGarantiaTramite.setFechaIngresoRegistroB(oResultSet.getDate("FECBINR"));
					oEGarantiaTramite.setFechaVigenciaAsientoB(oResultSet.getDate("FECBVAP"));
					oEGarantiaTramite.setDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")));
					oEGarantiaTramite.setDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")));
					oEGarantiaTramite.setFechaObservacionB(oResultSet.getDate("FECBOBS"));
					oEGarantiaTramite.setFechaTachaB(oResultSet.getDate("FECBTAC"));
					oEGarantiaTramite.setFechaInscripcionB(oResultSet.getDate("FECBINS"));
					oEGarantiaTramite.setFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")));
					oEGarantiaTramite.setTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")));
					oEGarantiaTramite.setCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")));
					oEGarantiaTramite.setTituloB(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULB")));
					
					oEGarantiaTramite.setFechaIngresoRegistro(oResultSet.getDate("FECINR"));
					oEGarantiaTramite.setFechaVigenciaAsiento(oResultSet.getDate("FECVAP"));
					oEGarantiaTramite.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")));
					oEGarantiaTramite.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")));				
					oEGarantiaTramite.setFechaObservacion(oResultSet.getDate("FECOBS"));
					oEGarantiaTramite.setFechaTacha(oResultSet.getDate("FECTAC"));
					oEGarantiaTramite.setFechaInscripcion(oResultSet.getDate("FECINS"));
					oEGarantiaTramite.setFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")));
					oEGarantiaTramite.setTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")));
					oEGarantiaTramite.setCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")));
					oEGarantiaTramite.setTituloA(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULA")));
					
					oEGarantiaTramite.setDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOTARIO")));
					lstGarantia.add(oEGarantiaTramite);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener Historico Tramite Garantia", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaPorConstituir(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAPORCONSTITUIR, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantia.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEGarantia.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEGarantia.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantia.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoEstadoRevision(oResultSet.getString("STATUS"));
					oEGarantia.setFechaRevision(oResultSet.getDate("FECREVF"));
					oEGarantia.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oEGarantia.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));	
					oEGarantia.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantia.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantia.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantia.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	
	public List<EGarantiaSolicitud> listarSolicitudAsociadaGarantia(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAASOCIADASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEGarantia.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEGarantia.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantia.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoEstadoRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("STATUS")));
					oEGarantia.setFechaRevision(oResultSet.getDate("FECREVF"));
					oEGarantia.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oEGarantia.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));	
					oEGarantia.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantia.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantia.setNumeroGarantiaReal(oResultSet.getInt("GARGAR"));
					oEGarantia.setCodigoEstadoGarantiaSolicitud(oResultSet.getInt("ESTADOSG"));
					oEGarantia.setDescripcionEstadoGarantiaSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTGAR")));
					oEGarantia.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantia.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					oEGarantia.setAbreviacionMonedaSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantia.setDescripcionMonedaSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setCodigoEstadoEvaluacionLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("STALEG")));
					//oEGarantia.setObservacion(oResultSet.getString("OBSERV"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarHistoricoGarantiaSolicitud(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_HISTORICOGARANTIASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantia.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					//oEGarantia.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					//oEGarantia.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					//oEGarantia.setCodigoEstadoRevision(oResultSet.getNString("STATUS"));
					oEGarantia.setFechaRevision(oResultSet.getDate("FECREVF"));
					oEGarantia.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oEGarantia.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));	
					oEGarantia.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantia.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					//oEGarantia.setCodigoEstadoGarantiaSolicitud(oResultSet.getInt("ESTSOLGAR"));
					//oEGarantia.setDescripcionEstadoGarantiaSolicitud(oResultSet.getString("DESCESTGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIPGAR")));
					oEGarantia.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantia> listarGarantiaVigente(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		List<EGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAVIGENTE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantia>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					oEGarantia.setFechaIngreso(oResultSet.getDate("FECING"));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setDescripcionEstadoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADOGARANT")));
					oEGarantia.setDescripcionSituacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSITUAC")));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
					oEGarantia.setDescripcionGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESSER")));
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantia> listarGarantia(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		List<EGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantia>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					//oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					//oEGarantia.setFechaIngreso(oResultSet.getDate("FECING"));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMONGAR")));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoSituacion(oResultSet.getInt("SITUAC"));
					oEGarantia.setDescripcionEstadoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADOGARANT")));
					oEGarantia.setDescripcionSituacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("SITUACGARANT")));
					oEGarantia.setCodigoInspector(oResultSet.getInt("CODISP"));
					oEGarantia.setPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEGarantia.setCodigoTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEGarantia.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEGarantia.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));		
					oEGarantia.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					//oEGarantia.setFechaVencimientoPoliza(oResultSet.getDate("FECVENCPOL"));
					oEGarantia.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaSolicitud(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud= null;
		List<EGarantiaSolicitud> lstGarantiaSolicitud = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantiaSolicitud = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantiaSolicitud = new EGarantiaSolicitud();
					oEGarantiaSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					//NUMERO REVISION
					oEGarantiaSolicitud.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantiaSolicitud.setCodigoServicioGarantia(oResultSet.getInt("SERGAR"));
					oEGarantiaSolicitud.setCodigoMonedaGarantia(oResultSet.getInt("MONGAR"));
					oEGarantiaSolicitud.setCuentaGarantia(oResultSet.getInt("CTAGAR"));
					oEGarantiaSolicitud.setDocumentoGarantia(oResultSet.getInt("DOCGAR"));
					oEGarantiaSolicitud.setNumeroGarantiaReal(oResultSet.getInt("GARGAR"));
					oEGarantiaSolicitud.setGrupoTanomoshi(oResultSet.getInt("GRUGAR"));
					oEGarantiaSolicitud.setNumeroListaTanomoshi(oResultSet.getInt("LISGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaOtro(UFuncionesGenerales.revisaCadena(oResultSet.getString("OTRGAR")));
					oEGarantiaSolicitud.setDescripcionGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantiaSolicitud.setMontoOrigenGarantia(oResultSet.getDouble("MTOORI"));
					//TIPO CAMBIO
					oEGarantiaSolicitud.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantiaSolicitud.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantiaSolicitud.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantiaSolicitud.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantiaSolicitud.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantiaSolicitud.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantiaSolicitud.setMotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("NMOTOR")));
					oEGarantiaSolicitud.setSerie(UFuncionesGenerales.revisaCadena(oResultSet.getString("CHASIS")));
					oEGarantiaSolicitud.setColor(UFuncionesGenerales.revisaCadena(oResultSet.getString("COLORV")));
					oEGarantiaSolicitud.setCodigoClase(oResultSet.getInt("CCLASE"));
					oEGarantiaSolicitud.setCodigoMarca(oResultSet.getInt("CMARCA"));
					oEGarantiaSolicitud.setCodigoModelo(oResultSet.getInt("CMODEL"));
					oEGarantiaSolicitud.setCodigoCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("CCOMBU")));
					oEGarantiaSolicitud.setCodigoNivelRiesgo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRIESG")));
					oEGarantiaSolicitud.setAnioFabricacion(oResultSet.getInt("AÑOFAB"));
					oEGarantiaSolicitud.setMontoValorizacion(oResultSet.getDouble("VALORI"));
					oEGarantiaSolicitud.setIndicadorNuevo(UFuncionesGenerales.revisaCadena(oResultSet.getString("VNUEVO")));
					
					oEGarantiaSolicitud.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCGARTIP")));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantiaSolicitud.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantiaSolicitud.setDescripcionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantiaSolicitud.setDescripcionServicio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSERGAR")));
					oEGarantiaSolicitud.setDescripcionGeneral(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCGEN")));
					
					lstGarantiaSolicitud.add(oEGarantiaSolicitud);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaSolicitud;
	}
	
	
	public List<EAsignacionContratoGarantia> listarCreditosAsociadosGarantia(long codigoGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EAsignacionContratoGarantia oEContratoGarantia= null;
		List<EAsignacionContratoGarantia> lstCreditoGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
				
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CREDITOSASOCIADOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstCreditoGarantia = new ArrayList<EAsignacionContratoGarantia>();
				while (oResultSet.next()) {
					oEContratoGarantia= new EAsignacionContratoGarantia();
					
					oEContratoGarantia.setNumeroContrato(oResultSet.getInt("NROCOT"));
					oEContratoGarantia.setNumeroContratoOtro(oResultSet.getInt("NROCOO"));
					oEContratoGarantia.setSecuenciaAsignacion(oResultSet.getInt("SECASI"));
					oEContratoGarantia.setTipoPrenda(oResultSet.getInt("TIPPDI"));
					oEContratoGarantia.setServicioBase(oResultSet.getInt("SEBASE"));
					oEContratoGarantia.setCodigoMonedaBaseAsignacion(oResultSet.getInt("MOBASE"));
					oEContratoGarantia.setServicio(oResultSet.getInt("SERVIC"));
					oEContratoGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEContratoGarantia.setNumeroOperacion(oResultSet.getLong("NUMOPE"));
					oEContratoGarantia.setNumeroPlanilla(oResultSet.getLong("PLANIL"));
					oEContratoGarantia.setNumeroFianza(oResultSet.getInt("NUMERO"));
					oEContratoGarantia.setNumeroOperacionTanomoshi(oResultSet.getInt("NOPTAN"));
					oEContratoGarantia.setNumeroLista(oResultSet.getInt("NROGRP"));
					oEContratoGarantia.setNumeroDocumentoDPF(oResultSet.getInt("NUMDOC"));
					oEContratoGarantia.setNumeroConsecutivoDPF(oResultSet.getInt("CONSEC"));
					oEContratoGarantia.setNumeroCuenta(oResultSet.getInt("CUENTA"));
					oEContratoGarantia.setMontoImporteDocMonedaOrigen(oResultSet.getDouble("IMPORI"));
					oEContratoGarantia.setTipoCambio(oResultSet.getDouble("CAMBTI"));
					oEContratoGarantia.setMontoImporteCubierto(oResultSet.getDouble("IMPCUB"));
					oEContratoGarantia.setMontoSaldoGarantia(oResultSet.getDouble("SLDGAR"));
					oEContratoGarantia.setPorcentajeCubiertoGarantia(oResultSet.getDouble("PORGAR"));
					oEContratoGarantia.setMontoImporteDocMonedaOrigen2(oResultSet.getDouble("IMPORS"));
					oEContratoGarantia.setMontoImporteCubierto2(oResultSet.getDouble("IMPCUS"));
					oEContratoGarantia.setMontoSaldoGarantia2(oResultSet.getDouble("SLDGAS"));
					oEContratoGarantia.setCodigoCliente(oResultSet.getInt("CODSOCIO"));
					oEContratoGarantia.setNombreCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMSOCIO")));
					oEContratoGarantia.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSE01")));
					oEContratoGarantia.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSE02")));
					oEContratoGarantia.setEstadoRegistro(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTARE")));
					oEContratoGarantia.setTipoRegistro(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIREGI")));
					oEContratoGarantia.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEContratoGarantia.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEContratoGarantia.setMontoSaldoCredito(oResultSet.getDouble("SALDOCREDITO"));
					EUsuario eUsuario = new EUsuario();
					eUsuario.setNombreUsuario(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUARI")));
					oEContratoGarantia.setUsuarioRegistro(eUsuario);
					oEContratoGarantia.setDescripcionEstadoCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADOCREDITO")));

					
					lstCreditoGarantia.add(oEContratoGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstCreditoGarantia;
	}
	
	public EMensaje eliminarPoliza(EPoliza ePoliza) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_POLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<EPoliza> listarPolizaSeguro(int codigo, String descripcion,String descripcion2) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EPoliza oEPoliza= null;
		List<EPoliza> lstPoliza = null;
		int codigoPoliza = 0;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(descripcion2);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CIASEGUROPOLIZA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstPoliza = new ArrayList<EPoliza>();
				while (oResultSet.next()) {
					codigoPoliza++;
					oEPoliza = new EPoliza();
					oEPoliza.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEPoliza.setNumeroPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEPoliza.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEPoliza.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEPoliza.setCodigoBrocker(oResultSet.getInt("BROCKE"));
					oEPoliza.setFechaInicioPoliza(oResultSet.getDate("FECIPO"));
					oEPoliza.setFechaVencimientoPoliza(oResultSet.getDate("FECPOL"));
					oEPoliza.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEPoliza.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEPoliza.setFechaEndoso(oResultSet.getDate("FECEND"));
					oEPoliza.setCodigoClienteUltimoEndoso(oResultSet.getInt("CLIEND"));
					oEPoliza.setNombreClienteUltimoEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMEND")));
					oEPoliza.setCodigoMonedaGarantia(oResultSet.getInt("MONEDA"));
					oEPoliza.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEPoliza.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
					oEPoliza.setCodigoPoliza(codigoPoliza);
					lstPoliza.add(oEPoliza);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener. Poliza", objEx);
		}
		return lstPoliza;
	}
	
	public List<EPoliza> buscarPolizaSeguro(EPoliza ePoliza) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EPoliza oEPoliza= null;
		List<EPoliza> lstPoliza = null;
		int codigoPoliza = 0;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CIASEGUROPOLIZA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstPoliza = new ArrayList<EPoliza>();
				while (oResultSet.next()) {
					codigoPoliza++;
					oEPoliza = new EPoliza();
					oEPoliza.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEPoliza.setNumeroPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEPoliza.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEPoliza.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEPoliza.setCodigoBrocker(oResultSet.getInt("BROCKE"));
					oEPoliza.setFechaInicioPoliza(oResultSet.getDate("FECIPO"));
					oEPoliza.setFechaVencimientoPoliza(oResultSet.getDate("FECPOL"));
					oEPoliza.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEPoliza.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEPoliza.setFechaEndoso(oResultSet.getDate("FECEND"));
					oEPoliza.setCodigoClienteUltimoEndoso(oResultSet.getInt("CLIEND"));
					oEPoliza.setNombreClienteUltimoEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMEND")));
					oEPoliza.setCodigoMonedaGarantia(oResultSet.getInt("MONEDA"));
					oEPoliza.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEPoliza.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
					oEPoliza.setCodigoPoliza(codigoPoliza);
					lstPoliza.add(oEPoliza);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener. Poliza", objEx);
		}
		return lstPoliza;
	}
		
	public EPoliza buscarPoliza(EPoliza ePoliza){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EPoliza oEPoliza= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_POLIZA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEPoliza = new EPoliza();
					oEPoliza.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEPoliza.setNumeroPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEPoliza.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEPoliza.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEPoliza.setCodigoBrocker(oResultSet.getInt("BROCKE"));
					oEPoliza.setFechaInicioPoliza(oResultSet.getDate("FECIPO"));
					oEPoliza.setFechaVencimientoPoliza(oResultSet.getDate("FECPOL"));
					oEPoliza.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEPoliza.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEPoliza.setFechaEndoso(oResultSet.getDate("FECEND"));
					oEPoliza.setCodigoClienteUltimoEndoso(oResultSet.getInt("CLIEND"));
					oEPoliza.setNombreClienteUltimoEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMEND")));
					oEPoliza.setCodigoMonedaGarantia(oResultSet.getInt("MONEDA"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEPoliza;
	}
	
	public EGarantia buscarUltimaGarantiaGenerada(){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ULTIMAGARANTIAGENERADA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantia;
	}
	
	public EGarantiaSolicitud buscarSolicitudxGarantia(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoGarantia);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOLICITUDXGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaSolicitud = new EGarantiaSolicitud();
					oEGarantiaSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantiaSolicitud.setNumeroGarantiaReal(oResultSet.getInt("GARANI"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaSolicitud;
	}
	
	
	
	public EGarantia buscarAnexoGarantia(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoGarantia);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ANEXOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoGarantia(oResultSet.getLong("GARANT"));
					oEGarantia.setUbicacion1Largo(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					oEGarantia.setUbicacion2Largo(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAB")));
					oEGarantia.setMontoComercial(oResultSet.getDouble("MONCOM"));
					oEGarantia.setFechaComercial(oResultSet.getDate("FECCOM"));
					oEGarantia.setPartidaRegistral(UFuncionesGenerales.revisaCadena(oResultSet.getString("PARREG")));
					oEGarantia.setOficinaRegistral(oResultSet.getInt("OFIREG"));
					oEGarantia.setTipoRegistral(oResultSet.getInt("TIPREG"));
					oEGarantia.setCodigoPropietario2(oResultSet.getInt("CODPR2"));
					oEGarantia.setCodigoPropietario3(oResultSet.getInt("CODPR3"));
					oEGarantia.setCodigoPropietario4(oResultSet.getInt("CODPR4"));
					oEGarantia.setCodigoPropietario5(oResultSet.getInt("CODPR5"));
					oEGarantia.setCodigoPropietario6(oResultSet.getInt("CODPR6"));
					oEGarantia.setDescripcionPropietario2(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR2DESC")));
					oEGarantia.setDescripcionPropietario3(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR3DESC")));
					oEGarantia.setDescripcionPropietario4(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR4DESC")));
					oEGarantia.setDescripcionPropietario5(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR5DESC")));
					oEGarantia.setDescripcionPropietario6(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR6DESC")));
					oEGarantia.setCodigoAsignacionInmueble(oResultSet.getInt("AGRUPA"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantia;
	}
	
	
	
	public EGarantiaTramite buscarUltimoAsientoGarantiaTramite(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoGarantia);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ULTASIENTOGARANTIATRAMITE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaTramite = new EGarantiaTramite();
					oEGarantiaTramite.setTituloA(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULA")));
					oEGarantiaTramite.setTituloB(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULB")));
					oEGarantiaTramite.setUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaTramite;
	}
	
	
	public EGarantia buscarCaracteristicaInmueblePredio(int nroSolicitud){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEOGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(nroSolicitud);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CARACTERISTICAINMUEBLEPREDIO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOGarantia = new EGarantia();
					oEOGarantia.setTipoInmueble(oResultSet.getInt("TIPINM"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOGarantia;
	}
	
	public EOperacionSolicitud buscarInstruccionAprobacionOperacionesF7320(long codigoSolicitud,int nroRevision){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEOPeracionSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoSolicitud);
			lstParametrosEntrada.add(nroRevision);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_INSTRUCCIONAPROBACIONOPERACIONES, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOPeracionSolicitud = new EOperacionSolicitud();
					oEOPeracionSolicitud.setCodigoClientePersona(oResultSet.getInt("CODCLI"));
					oEOPeracionSolicitud.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOPeracionSolicitud.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEOPeracionSolicitud.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEOPeracionSolicitud.setFechaRevision(oResultSet.getDate("FECSOL"));
					oEOPeracionSolicitud.setNombrePersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEOPeracionSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEOPeracionSolicitud.setCodigoTipoCredito(oResultSet.getInt("TIPCRE"));
					oEOPeracionSolicitud.setCodigoDestinoCredito(oResultSet.getInt("DESCRE"));
					oEOPeracionSolicitud.setCodigoStatus(oResultSet.getInt("STATUS"));
					oEOPeracionSolicitud.setCodigoMoneda(oResultSet.getInt("MONSOL"));
					oEOPeracionSolicitud.setDescripcionTipoCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIPCRE")));
					oEOPeracionSolicitud.setDescripcionDestinoCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESDESCRE")));
					oEOPeracionSolicitud.setAbreviacionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIP")));
					oEOPeracionSolicitud.setDescripcionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIPLARGO")));
					oEOPeracionSolicitud.setDescripcionStatus(UFuncionesGenerales.revisaCadena(oResultSet.getString("ADESEM")));	
					oEOPeracionSolicitud.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEOPeracionSolicitud.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEOPeracionSolicitud.setMontoAprobado(oResultSet.getDouble("MTOAPR"));
					oEOPeracionSolicitud.setMontoSolicitado(oResultSet.getDouble("MTOSOL"));
					oEOPeracionSolicitud.setNumeroContratoVehicular(oResultSet.getInt("NUMVEH"));
					oEOPeracionSolicitud.setTasaSolicitada(oResultSet.getDouble("TASSOL"));
					oEOPeracionSolicitud.setTasaAprobada(oResultSet.getDouble("TASAPR"));
					oEOPeracionSolicitud.setTipoTasaSolicitada(oResultSet.getInt("TTASOL"));
					oEOPeracionSolicitud.setTipoTasaAprobada(oResultSet.getInt("TTAAPR"));
					oEOPeracionSolicitud.setPlazoTotalAprobado(oResultSet.getInt("PLZAPR"));
					oEOPeracionSolicitud.setPlazoTotalSolicitado(oResultSet.getInt("PLZSOL"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOPeracionSolicitud;
	}
	
	public EOperacionSolicitud buscarUltimaRevisionSolicitudCredito(long codigoSolicitud){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEOPeracionSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoSolicitud);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ULTIMAREVISIONSOLICITUDCREDITO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOPeracionSolicitud = new EOperacionSolicitud();
					oEOPeracionSolicitud.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOPeracionSolicitud.setNumeroRevision(oResultSet.getInt("NREVIS"));		
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOPeracionSolicitud;
	}
	
	public EOperacionSolicitud buscarRegistroSolicitudCreditoF7301(long codigoSolicitud){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEOPeracionSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoSolicitud);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REGISTROSOLICITUDCREDITO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOPeracionSolicitud = new EOperacionSolicitud();
					oEOPeracionSolicitud.setCodigoClientePersona(oResultSet.getInt("CODCLI"));
					oEOPeracionSolicitud.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOPeracionSolicitud.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEOPeracionSolicitud.setNumeroRevision(oResultSet.getInt("ULTREV"));
					oEOPeracionSolicitud.setNombrePersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEOPeracionSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEOPeracionSolicitud.setCodigoTipoCredito(oResultSet.getInt("TIPCRE"));
					oEOPeracionSolicitud.setCodigoDestinoCredito(oResultSet.getInt("DESCRE"));
					oEOPeracionSolicitud.setCodigoMoneda(oResultSet.getInt("MONSOL"));
					oEOPeracionSolicitud.setAbreviacionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIP")));
					oEOPeracionSolicitud.setDescripcionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIPLARGO")));	
					oEOPeracionSolicitud.setDescripcionTipoCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESTIPCRE")));
					oEOPeracionSolicitud.setDescripcionDestinoCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESDESCRE")));
					oEOPeracionSolicitud.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEOPeracionSolicitud.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEOPeracionSolicitud.setMontoSolicitado(oResultSet.getDouble("MTOSOL"));

				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOPeracionSolicitud;
	}
	
	
	public EContrato buscarMaestroContratoF7401(long numeroContrato){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EContrato oEContrato= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(numeroContrato);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_MAESTROCONTRATO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEContrato = new EContrato();
					oEContrato.setNumeroContrato(oResultSet.getInt("NROCOT"));
					oEContrato.setCodigoMonedaPagare(oResultSet.getInt("MONEDA"));
					oEContrato.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEContrato.setCodigoAsociado(oResultSet.getInt("CODASO"));
					oEContrato.setFirmaConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("COYFIR")));
					oEContrato.setNombreSocioCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEContrato.setNombreSocioLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEContrato.setCodigoTipoContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("TPOCOT")));
					oEContrato.setCodigoCompania(oResultSet.getInt("CODCIA"));
					oEContrato.setCodigoRepresentante(oResultSet.getLong("CODREP"));
					oEContrato.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEContrato.setNumeroOperacion(oResultSet.getInt("NUMOPE"));
					oEContrato.setNumeroLista(oResultSet.getInt("NLISTA"));
					oEContrato.setImporteAprobado(oResultSet.getDouble("IMPRTE"));
					oEContrato.setImporteAsignado(oResultSet.getDouble("IMPASI"));
					oEContrato.setTasaInteresMostrada(oResultSet.getDouble("TASA"));
					oEContrato.setCodigoPeriodo(oResultSet.getInt("PERIOD"));
					oEContrato.setPlazoDias(oResultSet.getInt("PLAZO"));
					oEContrato.setIndicadorArchivoLogico(oResultSet.getInt("LO7401"));

				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEContrato;
	}
	
	
	public EContrato buscarUltimaContratoGarantiaGeneradoF7401(){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EContrato oEOContrato= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ULTIMOCONTRATOGARANTIAGENERADO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOContrato = new EContrato();
					oEOContrato.setNumeroContrato(oResultSet.getLong("NROCOT"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOContrato;
	}
	
	public EDocumentoGenerado buscarMaestroDocumentoGeneradoF7420(EDocumentoGenerado eDocumentoGenerado){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EDocumentoGenerado oEODocumentoGenerado= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(eDocumentoGenerado.getTipoPlantilla());
			lstParametrosEntrada.add(eDocumentoGenerado.getMonedaPlantilla());
			lstParametrosEntrada.add(eDocumentoGenerado.getTipoVariante());
			lstParametrosEntrada.add(eDocumentoGenerado.getNumeroDocumento());
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_MAESTRODOCUMENTOGENERADO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEODocumentoGenerado = new EDocumentoGenerado();
					oEODocumentoGenerado.setTipoPlantilla(oResultSet.getInt("TIPOPL"));
					oEODocumentoGenerado.setMonedaPlantilla(oResultSet.getInt("MONEPL"));
					oEODocumentoGenerado.setTipoVariante(oResultSet.getInt("TIPOVA"));
					oEODocumentoGenerado.setNumeroDocumento(oResultSet.getLong("NRODOC"));
					oEODocumentoGenerado.setSimboloMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("MONSIM")));
					oEODocumentoGenerado.setImporteDocumento(oResultSet.getDouble("IMPNUM"));
					oEODocumentoGenerado.setImporteEnNumeroAlfa(UFuncionesGenerales.revisaCadena(oResultSet.getString("IMPALF")));
					oEODocumentoGenerado.setImporteEnLetras(UFuncionesGenerales.revisaCadena(oResultSet.getString("IMPLET")));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEODocumentoGenerado;
	}
	
	public EPoliza buscarPolizaAsociadoGarantiaMaxCorrelativo(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EPoliza oEPolizaGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoGarantia);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_POLIZAASOCIADAGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEPolizaGarantia = new EPoliza();
					oEPolizaGarantia.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEPolizaGarantia.setNumeroPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEPolizaGarantia.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEPolizaGarantia.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEPolizaGarantia.setCodigoBrocker(oResultSet.getInt("BROCKE"));
					oEPolizaGarantia.setFechaInicioPoliza(oResultSet.getDate("FECIPO"));
					oEPolizaGarantia.setFechaVencimientoPoliza(oResultSet.getDate("FECPOL"));
					oEPolizaGarantia.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEPolizaGarantia.setCodigoMonedaGarantia(oResultSet.getInt("MONEDA"));
					oEPolizaGarantia.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEPolizaGarantia.setFechaEndoso(oResultSet.getDate("FECEND"));
					oEPolizaGarantia.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEPolizaGarantia.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
					oEPolizaGarantia.setDescripcionBrockerSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCBROC")));
					oEPolizaGarantia.setAbreviacionMonedaPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEPolizaGarantia.setDescripcionMonedaPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEPolizaGarantia.setDescripcionEstadoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCEST")));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEPolizaGarantia;
	}
	
	public List<ESaldoServicio> obtenerSaldosServiciosCliente(int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ESaldoServicio oESaldoServicio= null;
		List<ESaldoServicio> lstSaldoServicio = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CRED_DIR_IND_GARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstSaldoServicio = new ArrayList<ESaldoServicio>();
				while (oResultSet.next()) {
					oESaldoServicio = new ESaldoServicio();
					oESaldoServicio.setClaseServicio(oResultSet.getInt("CLASER"));
					oESaldoServicio.setCodigoGrupoEconomico(oResultSet.getInt("GRUPOE"));
					oESaldoServicio.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oESaldoServicio.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oESaldoServicio.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oESaldoServicio.setMontoSaldo(oResultSet.getDouble("MONTOS"));
					oESaldoServicio.setMontoAcumulado(oResultSet.getDouble("MONSAL"));
					oESaldoServicio.setMontoTotalComision(oResultSet.getDouble("MONCOM"));
					oESaldoServicio.setMontoTotalInteres(oResultSet.getDouble("MONINT"));
					oESaldoServicio.setMontoTotalCuenta(oResultSet.getDouble("TOTCUE"));
					
					lstSaldoServicio.add(oESaldoServicio);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener Saldo de Servicio por Cliente", objEx);
		}
		return lstSaldoServicio;
	}
	
	public ETipoCambio buscarTipoCambioDiario(){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ETipoCambio oETipoCambio= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_TIPOCAMBIODIARIO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oETipoCambio=new ETipoCambio();
					oETipoCambio.setAnio(oResultSet.getInt("AÑO"));
					oETipoCambio.setMes(oResultSet.getInt("MES"));
					oETipoCambio.setValorPromedioMes(oResultSet.getDouble("VPROMD"));
					oETipoCambio.setCompraDiaria(oResultSet.getDouble("COMPRADIARIA"));
					oETipoCambio.setVentaDiaria(oResultSet.getDouble("VENTADIARIA"));		
					oETipoCambio.setTipoCambioSBS(oResultSet.getDouble("SBS"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETipoCambio;
	}
	
	public List<EGarantiaDocumentoSolicitado> listarDocumentoSolicitado(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaDocumentoSolicitado oEGarantiaDocumentoSolicitado= null;
		EUsuario oEUsuario = null;
		List<EGarantiaDocumentoSolicitado> lstGarantiaDocumentoSolicitado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIADOCUMENTOSOLICITADO, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstGarantiaDocumentoSolicitado = new ArrayList<EGarantiaDocumentoSolicitado>();
				while (oResultSet.next()) {
					oEGarantiaDocumentoSolicitado = new EGarantiaDocumentoSolicitado();
					oEUsuario = new EUsuario();
					
					oEGarantiaDocumentoSolicitado.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaDocumentoSolicitado.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantiaDocumentoSolicitado.setCodigoTipoDocumento(oResultSet.getInt("TDOCRE"));
					oEGarantiaDocumentoSolicitado.setNumeroLineaDocumento(oResultSet.getInt("NLINDO"));
					oEGarantiaDocumentoSolicitado.setDescripcionDocumentoSolicitado(UFuncionesGenerales.revisaCadena(oResultSet.getString("DDOCUM")));
					oEGarantiaDocumentoSolicitado.setCodigoTipoOrigenSolicitado(UFuncionesGenerales.revisaCadena(oResultSet.getString("ORICOP")));
					oEGarantiaDocumentoSolicitado.setIndicadorTraido(UFuncionesGenerales.revisaCadena(oResultSet.getString("SWTRAE")));
					oEGarantiaDocumentoSolicitado.setDescripcionDocumentoTraido(UFuncionesGenerales.revisaCadena(oResultSet.getString("TRADOC")));
					oEGarantiaDocumentoSolicitado.setCodigoTipoOrigenTraido(UFuncionesGenerales.revisaCadena(oResultSet.getString("TRAOCP")));
					oEGarantiaDocumentoSolicitado.setFechaTraido(oResultSet.getDate("FECINS"));
					oEGarantiaDocumentoSolicitado.setFechaIngreso(oResultSet.getDate("FECTRS"));
					oEGarantiaDocumentoSolicitado.setHoraIngreso(oResultSet.getString("HORINS"));
					oEGarantiaDocumentoSolicitado.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEGarantiaDocumentoSolicitado.setHoraRegistro(UFuncionesGenerales.revisaCadena(oResultSet.getString("HORREG")));
					
					oEGarantiaDocumentoSolicitado.setDescripcionTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTDO")));
					oEGarantiaDocumentoSolicitado.setDescripcionTipoOrigenSolicitado(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTORISOL")));
					oEUsuario.setNombreUsuario(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEGarantiaDocumentoSolicitado.setUsuarioRegistro(oEUsuario);
					
					lstGarantiaDocumentoSolicitado.add(oEGarantiaDocumentoSolicitado);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaDocumentoSolicitado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoVigenteRelacionado(long codigo) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaCreditoRelacionado oEGarantiaCreditoRelacionado= null;
		List<EGarantiaCreditoRelacionado> lstGarantiaCreditoRelacionado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CREDITOVIGENTERELACIONADOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantiaCreditoRelacionado = new ArrayList<EGarantiaCreditoRelacionado>();
				while (oResultSet.next()) {
					oEGarantiaCreditoRelacionado = new EGarantiaCreditoRelacionado();
					oEGarantiaCreditoRelacionado.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantiaCreditoRelacionado.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantiaCreditoRelacionado.setNumeroOperacion(oResultSet.getInt("NUMOPE"));
					lstGarantiaCreditoRelacionado.add(oEGarantiaCreditoRelacionado);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaCreditoRelacionado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoCanceladoRelacionado(long codigo) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaCreditoRelacionado oEGarantiaCreditoRelacionado= null;
		List<EGarantiaCreditoRelacionado> lstGarantiaCreditoRelacionado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CREDITOCANCELADORELACIONADOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantiaCreditoRelacionado = new ArrayList<EGarantiaCreditoRelacionado>();
				while (oResultSet.next()) {
					oEGarantiaCreditoRelacionado = new EGarantiaCreditoRelacionado();
					oEGarantiaCreditoRelacionado.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantiaCreditoRelacionado.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantiaCreditoRelacionado.setNumeroOperacion(oResultSet.getInt("NUMOPE"));
					lstGarantiaCreditoRelacionado.add(oEGarantiaCreditoRelacionado);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaCreditoRelacionado;
	}
	
	public EGarantia buscarGarantia(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantia=new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));	
					oEGarantia.setCodigoSucursal(oResultSet.getInt("SUCURS"));
					oEGarantia.setCodigoAgenciaEmisora(oResultSet.getInt("AGEUSR"));
					oEGarantia.setCodigoDepartamentoEmisor(oResultSet.getInt("UNOPCT"));
					oEGarantia.setDescripcionUsuarioEmisor(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUARI")));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setGrupoEconomico(oResultSet.getInt("GRUPOE"));
					oEGarantia.setCodigoPropietario(oResultSet.getInt("CODPRO"));
					oEGarantia.setCodigoAval2(oResultSet.getInt("CODAV2"));
					oEGarantia.setCodigoAval3(oResultSet.getInt("CODAV3"));
					oEGarantia.setCodigoAval4(oResultSet.getInt("CODAV4"));
					oEGarantia.setCodigoTipoAval1(oResultSet.getInt("TIPAV1"));
					oEGarantia.setCodigoTipoAval2(oResultSet.getInt("TIPAV2"));
					oEGarantia.setCodigoTipoAval3(oResultSet.getInt("TIPAV3"));
					oEGarantia.setCodigoTipoAval4(oResultSet.getInt("TIPAV4"));			
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setHojaIngresoLegal(oResultSet.getInt("HOJINL"));
					oEGarantia.setTipoVariable1(oResultSet.getInt("TIPGA1"));
					oEGarantia.setTipoVariable2(oResultSet.getInt("TIPGA2"));
					oEGarantia.setTipoVariable3(oResultSet.getInt("TIPGA3"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoSituacion(oResultSet.getInt("SITUAC"));
					oEGarantia.setOrigenGarantia(oResultSet.getInt("ORIGEN"));
					oEGarantia.setDesplazamiento(oResultSet.getInt("DESPLA"));
					oEGarantia.setJudicial(oResultSet.getInt("JUDICI"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
					oEGarantia.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("NUMDOC")));
					oEGarantia.setDescripcionA(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRI")));
					oEGarantia.setDescripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRB")));
					oEGarantia.setDescripcionC(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRC")));
					oEGarantia.setDescripcionD(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRD")));
					oEGarantia.setComentario(UFuncionesGenerales.revisaCadena(oResultSet.getString("COMENT")));
					oEGarantia.setNumeroPropuesta(oResultSet.getInt("NROPRO"));
					oEGarantia.setTransaccionOrigen(oResultSet.getInt("TRAORI"));
					oEGarantia.setMonedaCredito(oResultSet.getInt("MONCRE"));
					oEGarantia.setGarantizadoActivos(oResultSet.getInt("GARACT"));
					oEGarantia.setInformacionAdicional(oResultSet.getInt("INFADI"));
					oEGarantia.setCodigoTasador(oResultSet.getInt("CODTAS"));
					oEGarantia.setCodigoUbigeo(oResultSet.getInt("UBIGEO"));
					oEGarantia.setFechaIngreso(oResultSet.getDate("FECING"));
					oEGarantia.setFechaUltimoMovimiento(oResultSet.getDate("FECVAR1"));
					oEGarantia.setFechaVencimientoPoliza(oResultSet.getDate("FECVENCPOL"));

					oEGarantia.setFechaGravamen(oResultSet.getDate("FECGRAV"));
					oEGarantia.setFechaValoracion(oResultSet.getDate("FECVALORACION"));
					oEGarantia.setFechaVariable1(oResultSet.getDate("FECVAR1"));
					oEGarantia.setFechaVariable2(oResultSet.getDate("FECVAR2"));
					oEGarantia.setFechaVariable3(oResultSet.getDate("FECVAR3"));
					oEGarantia.setFechaVariable4(oResultSet.getDate("FECVAR4"));
					oEGarantia.setFechaVencimiento(oResultSet.getDate("FECVENCIMIENTO"));
					oEGarantia.setFechaEndoso(oResultSet.getDate("FECENDOSO"));
					oEGarantia.setFechaPropuesta(oResultSet.getDate("FECPROPUESTA"));		
					oEGarantia.setFechaUltimaInspeccion(oResultSet.getDate("FECULTINSPECCION"));
					oEGarantia.setFechaUltimoEstadoRegistrado(oResultSet.getDate("FECULTESTREGIST"));
					oEGarantia.setFechaRemate(oResultSet.getDate("FECREMATE"));	
					oEGarantia.setFechaLiberacion(oResultSet.getDate("FECLIBERACION"));
					oEGarantia.setFechaEjecucion(oResultSet.getDate("FECEJECUCION"));

					oEGarantia.setMontoOriginalGarantia(oResultSet.getDouble("MONORI"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MONTOG"));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setMontoValoracion(oResultSet.getDouble("MONTOV"));
					oEGarantia.setMontoCredito(oResultSet.getDouble("MTOCRE"));
					oEGarantia.setMontoEjecucion(oResultSet.getDouble("MTOEJE"));
					oEGarantia.setMontoVariable1(oResultSet.getDouble("MONTO1"));
					oEGarantia.setMontoVariable2(oResultSet.getDouble("MONTO2"));
					oEGarantia.setMontoVariable3(oResultSet.getDouble("MONTO3"));
					oEGarantia.setMontoVariable4(oResultSet.getDouble("MONTO4"));
					
					oEGarantia.setCodigoInspector(oResultSet.getInt("CODISP"));
					oEGarantia.setCodigoTipoBien(oResultSet.getInt("TIPBIE"));
					oEGarantia.setDepositario(oResultSet.getInt("DEPOSI"));
					oEGarantia.setAlmacen(oResultSet.getInt("ALMACE"));
					oEGarantia.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantia.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantia.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantia.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantia.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantia.setNumeroVariable1(oResultSet.getInt("NUMER1")); 
					oEGarantia.setNumeroVariable2(oResultSet.getInt("NUMER2")); 
					oEGarantia.setNumeroVariable3(oResultSet.getInt("NUMER3")); 				
					oEGarantia.setNumeroVariable4(oResultSet.getInt("NUMER4")); 
					oEGarantia.setAnioFabricacion(oResultSet.getInt("AÑOVAR")); 
					oEGarantia.setTipoAlmacen(oResultSet.getInt("TIPALM"));
					oEGarantia.setBancoEmisor(oResultSet.getInt("BANCO"));
					oEGarantia.setCuenta(oResultSet.getInt("SERDOC"));
					oEGarantia.setRangoHipoteca(UFuncionesGenerales.revisaCadena(oResultSet.getString("RANHIP")));
					oEGarantia.setUsuarioUltimaModificacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUMOD")));		
					oEGarantia.setMarcaProtestado(oResultSet.getInt("PROTES"));
					oEGarantia.setMarcaRemate(oResultSet.getInt("REMATE"));
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));	
					oEGarantia.setUbicacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAB")));
					oEGarantia.setPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEGarantia.setCodigoTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEGarantia.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEGarantia.setDescripcionUsoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRI")));
					oEGarantia.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));				
					oEGarantia.setDescripcionTipoBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPBIEN")));
					oEGarantia.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEGarantia.setDescripcionSituacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSITUAC")));
					oEGarantia.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantia.setDescripcionEstadoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCEST")));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantia.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEGarantia.setDescripcionBanco(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCBANCO")));
					oEGarantia.setDescripcionTipoFiador(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPOFIADOR")));
					oEGarantia.setDescripcionTipoFianza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPOFIANZA")));
					oEGarantia.setDescripcionTipoPlazo(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPOPLAZO")));
					oEGarantia.setDescripcionPropietario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCPROPIETARIOBIEN")));
					oEGarantia.setDescripcionTasador(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTASADOR")));
					oEGarantia.setDescripcionDepositario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCDEPOSITARIO")));
					oEGarantia.setMontoDisponible(oResultSet.getDouble("MTODIS"));
					oEGarantia.setSaldoDisponible(oResultSet.getDouble("SLDDIS"));
					oEGarantia.setPorcentajeDisponible(oResultSet.getDouble("PORDIS"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantia;
	}
	
	
	
	
	public EGarantiaTramite buscarGarantiaTramite(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIATRAMITE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaTramite=new EGarantiaTramite();
					oEGarantiaTramite.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantiaTramite.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantiaTramite.setFechaIngreso(oResultSet.getDate("FECINL"));
					oEGarantiaTramite.setFechaElaboracionContrato(oResultSet.getDate("FECELC"));
					oEGarantiaTramite.setFechaFirmaContrato(oResultSet.getDate("FECFIC"));
					oEGarantiaTramite.setFechaIngresoNotaria(oResultSet.getDate("FECFIN"));
					oEGarantiaTramite.setFechaLegalizacionFirma(oResultSet.getDate("FECLEF"));
					oEGarantiaTramite.setFechaIngresoRegistro(oResultSet.getDate("FECINR"));
					oEGarantiaTramite.setFechaVigenciaAsiento(oResultSet.getDate("FECVAP"));
					oEGarantiaTramite.setFechaObservacion(oResultSet.getDate("FECOBS"));
					oEGarantiaTramite.setFechaTacha(oResultSet.getDate("FECTAC"));
					oEGarantiaTramite.setFechaInscripcion(oResultSet.getDate("FECINS"));
					oEGarantiaTramite.setNumeroKardex(oResultSet.getInt("KARDEN"));
					oEGarantiaTramite.setCodigoNotario(oResultSet.getInt("NOTARI"));
					oEGarantiaTramite.setFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")));
					oEGarantiaTramite.setTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")));
					oEGarantiaTramite.setCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")));
					oEGarantiaTramite.setNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL"));
					oEGarantiaTramite.setDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")));
					oEGarantiaTramite.setDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")));
					oEGarantiaTramite.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")));
					oEGarantiaTramite.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")));
					oEGarantiaTramite.setEvaluacionDocumento(oResultSet.getInt("EVADOC"));
					oEGarantiaTramite.setUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")));
					
					oEGarantiaTramite.setFechaIngresoRegistroB(oResultSet.getDate("FECBINR"));
					oEGarantiaTramite.setFechaVigenciaAsientoB(oResultSet.getDate("FECBVAP"));
					oEGarantiaTramite.setFechaObservacionB(oResultSet.getDate("FECBOBS"));
					oEGarantiaTramite.setFechaTachaB(oResultSet.getDate("FECBTAC"));
					oEGarantiaTramite.setFechaInscripcionB(oResultSet.getDate("FECBINS"));
					oEGarantiaTramite.setFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")));
					oEGarantiaTramite.setTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")));
					oEGarantiaTramite.setCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")));
					oEGarantiaTramite.setDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOTARIO")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaTramite;
	}
	
	public EGarantiaSolicitud buscarGarantiaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(secuenciaGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaSolicitud=new EGarantiaSolicitud();
					oEGarantiaSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					//NUMERO REVISION
					oEGarantiaSolicitud.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantiaSolicitud.setCodigoServicioGarantia(oResultSet.getInt("SERGAR"));
					oEGarantiaSolicitud.setCodigoMonedaGarantia(oResultSet.getInt("MONGAR"));
					oEGarantiaSolicitud.setCuentaGarantia(oResultSet.getInt("CTAGAR"));
					oEGarantiaSolicitud.setDocumentoGarantia(oResultSet.getInt("DOCGAR"));
					oEGarantiaSolicitud.setNumeroGarantiaReal(oResultSet.getInt("GARGAR"));
					oEGarantiaSolicitud.setGrupoTanomoshi(oResultSet.getInt("GRUGAR"));
					oEGarantiaSolicitud.setNumeroListaTanomoshi(oResultSet.getInt("LISGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaOtro(UFuncionesGenerales.revisaCadena(oResultSet.getString("OTRGAR")));
					oEGarantiaSolicitud.setDescripcionGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantiaSolicitud.setMontoOrigenGarantia(oResultSet.getDouble("MTOORI"));
					//TIPO CAMBIO
					oEGarantiaSolicitud.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantiaSolicitud.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantiaSolicitud.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantiaSolicitud.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantiaSolicitud.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantiaSolicitud.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantiaSolicitud.setMotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("NMOTOR")));
					oEGarantiaSolicitud.setSerie(UFuncionesGenerales.revisaCadena(oResultSet.getString("CHASIS")));
					oEGarantiaSolicitud.setColor(UFuncionesGenerales.revisaCadena(oResultSet.getString("COLORV")));
					oEGarantiaSolicitud.setCodigoClase(oResultSet.getInt("CCLASE"));
					oEGarantiaSolicitud.setCodigoMarca(oResultSet.getInt("CMARCA"));
					oEGarantiaSolicitud.setCodigoModelo(oResultSet.getInt("CMODEL"));
					oEGarantiaSolicitud.setCodigoCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("CCOMBU")));
					oEGarantiaSolicitud.setDescripcionCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("COMBUS")));
					oEGarantiaSolicitud.setCodigoNivelRiesgo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRIESG")));
					oEGarantiaSolicitud.setAnioFabricacion(oResultSet.getInt("AÑOFAB"));
					oEGarantiaSolicitud.setMontoValorizacion(oResultSet.getDouble("VALORI"));
					oEGarantiaSolicitud.setIndicadorNuevo(UFuncionesGenerales.revisaCadena(oResultSet.getString("VNUEVO")));
					
					oEGarantiaSolicitud.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTGAR")));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTGARR")));
					oEGarantiaSolicitud.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantiaSolicitud.setDescripcionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaSolicitud;
	}
	
	public EGarantiaSolicitud buscarGarantiaAsociadaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(secuenciaGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIAASOCIADASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaSolicitud=new EGarantiaSolicitud();
					oEGarantiaSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					//oEGarantiaSolicitud.setNumeroRevision(oResultSet.getInt("NREVIS"));
					//oEGarantiaSolicitud.setNombreCorto(oResultSet.getString("NOMABV"));
					//oEGarantiaSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					//oEGarantiaSolicitud.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					//oEGarantiaSolicitud.setCodigoEstadoRevision(oResultSet.getString("STATUS"));
					//oEGarantiaSolicitud.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					//oEGarantiaSolicitud.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));
					oEGarantiaSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantiaSolicitud.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					oEGarantiaSolicitud.setCodigoEstadoGarantiaSolicitud(oResultSet.getInt("ESTADOSG"));
					oEGarantiaSolicitud.setUsuarioCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEGarantiaSolicitud.setFechaCredito(oResultSet.getDate("FECREG"));
					oEGarantiaSolicitud.setUsuarioLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USULEG")));
					oEGarantiaSolicitud.setFechaLegal(oResultSet.getDate("FECLEG"));			
					oEGarantiaSolicitud.setObservacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSERV")));
					oEGarantiaSolicitud.setMontoTasacion(oResultSet.getDouble("MONTAS"));
					oEGarantiaSolicitud.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantiaSolicitud.setMontoValorRealizacion(oResultSet.getDouble("MONTOV"));
					oEGarantiaSolicitud.setPorcentajeAsignado(oResultSet.getDouble("PORCUSO"));
					oEGarantiaSolicitud.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEGarantiaSolicitud.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEGarantiaSolicitud.setPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEGarantiaSolicitud.setValorPoliza(oResultSet.getDouble("VALPOL"));	
					oEGarantiaSolicitud.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEGarantiaSolicitud.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEGarantiaSolicitud.setFechaVencimientoPoliza(oResultSet.getDate("FECVENCPOLIZA"));
					oEGarantiaSolicitud.setSaldoDisponible(oResultSet.getDouble("SLDDIS"));
					oEGarantiaSolicitud.setFechaComercial(oResultSet.getDate("FECCOM"));
					oEGarantiaSolicitud.setMontoComercial(oResultSet.getDouble("MONCOM"));
					//oEGarantiaSolicitud.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaSolicitud;
	}
	
	public EGarantiaDetalleSolicitud buscarGarantiaDetalleSolicitud(long numeroSolicitud, int secuenciaGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(secuenciaGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIADETALLESOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaDetalleSolicitud = new EGarantiaDetalleSolicitud();
					oEGarantiaDetalleSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaDetalleSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantiaDetalleSolicitud.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantiaDetalleSolicitud.setDescripcionGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantiaDetalleSolicitud.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantiaDetalleSolicitud.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MAMARC")));
					oEGarantiaDetalleSolicitud.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MAMODE")));
					oEGarantiaDetalleSolicitud.setSerie(UFuncionesGenerales.revisaCadena(oResultSet.getString("MASERI")));
					oEGarantiaDetalleSolicitud.setMotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("MAMOTO")));
					oEGarantiaDetalleSolicitud.setCantidad(oResultSet.getInt("MACANT"));
					oEGarantiaDetalleSolicitud.setCodigoTipoPrenda(oResultSet.getInt("MATPRE"));
					oEGarantiaDetalleSolicitud.setDireccion(UFuncionesGenerales.revisaCadena(oResultSet.getString("PRDIRE")));
					oEGarantiaDetalleSolicitud.setCodigoUbigeo(oResultSet.getInt("PRUBIG"));
					oEGarantiaDetalleSolicitud.setAreaTerreno(oResultSet.getDouble("PRATER"));
					oEGarantiaDetalleSolicitud.setAreaConstruida(oResultSet.getDouble("PRACON"));
					oEGarantiaDetalleSolicitud.setNumeroPisos(oResultSet.getInt("PRPISO"));
					oEGarantiaDetalleSolicitud.setUsoPredio(UFuncionesGenerales.revisaCadena(oResultSet.getString("PRUSOP")));
					oEGarantiaDetalleSolicitud.setCodigoTipoPrenda2(oResultSet.getInt("PRTPRE"));
					
					oEGarantiaDetalleSolicitud.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTGARR")));
					//oEGarantiaDetalleSolicitud.setDescripcionTipoPrenda(UFuncionesGenerales.revisaCadena(oResultSet.getString("")));
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaDetalleSolicitud;
	}
}
