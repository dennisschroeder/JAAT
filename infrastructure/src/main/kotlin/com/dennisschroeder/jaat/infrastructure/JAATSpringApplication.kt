package com.dennisschroeder.jaat.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.runApplication
import com.dennisschroeder.jaat.infrastructure.beanDeclarations

@EnableJpaRepositories("com.dennisschroeder.jaat.drivenadapter")
@EntityScan("com.dennisschroeder.jaat.drivenadapter")
@SpringBootApplication
class JAATSpringApplication

fun main(args: Array<String>) {
    runApplication<JAATSpringApplication> {
        addInitializers(beanDeclarations())
    }
}
