package com.dang.travel;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/")
public class CiCdTestController {
	@Operation(summary = "health check용")
	@GetMapping
	public String getServerIp() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverIp = inetAddress.getHostAddress();
			return "Hello World! Server IP address is " + serverIp;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "Hello World! Could not determine server IP address";
		}
	}
}
