package com.dang.travel;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CICDTestController {
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
