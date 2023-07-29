package com.atorres.nttdata.debitmsf.controller;

import com.atorres.nttdata.debitmsf.model.DebitDto;
import com.atorres.nttdata.debitmsf.model.RequestAddDebit;
import com.atorres.nttdata.debitmsf.model.RequestDebit;
import com.atorres.nttdata.debitmsf.service.DebitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/debit")
@Slf4j
public class DebitController {
	@Autowired
	private DebitService debitService;
	/**
	 * Metodo para traer debito por su id
	 * @param debitId id cliente
	 * @return debitdto
	 */
	@GetMapping(value = "/{debitId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<DebitDto> getDebit(
					@PathVariable String debitId){
		return debitService.getDebit(debitId)
						.doOnNext(account -> log.info("Debito encontrada con exito"));
	}
	/**
	 * Metodo para traer todas las tarjetas debito asociadas al cliente
	 * @param clientId id cliente
	 * @return debitdto
	 */
	@GetMapping(value = "/client/{clientId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<DebitDto> getDebitClient(
					@PathVariable String clientId){
		return debitService.getDebitClient(clientId)
						.doOnNext(account -> log.info("Debito encontrada con exito"));
	}

	/**
	 * Metodo que crea un tarjeta debito y enlaza con la cuenta principal
	 * @param request request debito
	 * @return debitdto
	 */
	@PostMapping(value = "/create",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<DebitDto> createDebit(@RequestBody RequestDebit request){
		return debitService.createDebit(request)
						.doOnSuccess(v -> log.info("Debito creado con exito"));
	}

	/**
	 * Metodo para traer el balance de la cuenta principal
	 * @param debitId id debito
	 * @return balance
	 */
	@GetMapping(value = "/main-balance/{debitId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<BigDecimal> getMainProduct(@PathVariable String debitId){
		return debitService.getMainBalance(debitId)
						.doOnSuccess(v -> log.info("Balance de la cuenta principal obtenida con exito"));
	}

	/**
	 * Metodo que trae el balance de todas las cuentas en la tarjeta debito
	 * @param debitId debit id
	 * @return balance total
	 */
	@GetMapping(value = "/all-balance/{debitId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<BigDecimal> getAllBalance(@PathVariable String debitId){
		return debitService.getAllBalance(debitId)
						.doOnSuccess(v -> log.info("Balance de la cuenta principal obtenida con exito"));
	}
	/**
	 * Metodo para agregar una cuenta al debito
	 * @param request requet para agregar una cuenta
	 * @return debitdto
	 */
	@PatchMapping(value = "/add")
	public Mono<DebitDto> addProductDebit(@RequestBody RequestAddDebit request){
		return debitService.addAccountDebit(request)
						.doOnSuccess(v -> log.info("Cuenta agregada con exito"));
	}

}
