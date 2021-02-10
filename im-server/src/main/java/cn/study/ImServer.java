package cn.study;

import cn.study.im.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/09
 */
@MapperScan(basePackages = "cn.study.**.mapper")
@SpringBootApplication
public class ImServer {
    public static void main(String[] args) {
        SpringApplication.run(ImServer.class,args);
        new NettyServer().startServer(2048);
        System.out.println("\n" +
                "          __                 __  .__                                                                 \n" +
                "  _______/  |______ ________/  |_|__| ____    ____     ________ __   ____  ____  ____   ______ ______\n" +
                " /  ___/\\   __\\__  \\\\_  __ \\   __\\  |/    \\  / ___\\   /  ___/  |  \\_/ ___\\/ ___\\/ __ \\ /  ___//  ___/\n" +
                " \\___ \\  |  |  / __ \\|  | \\/|  | |  |   |  \\/ /_/  >  \\___ \\|  |  /\\  \\__\\  \\__\\  ___/ \\___ \\ \\___ \\ \n" +
                "/____  > |__| (____  /__|   |__| |__|___|  /\\___  /  /____  >____/  \\___  >___  >___  >____  >____  >\n" +
                "     \\/            \\/                    \\//_____/        \\/            \\/    \\/    \\/     \\/     \\/ \n");
    }
}
