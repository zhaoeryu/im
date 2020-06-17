package cn.wecloud;

import cn.wecloud.layim.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/09
 */
@MapperScan(basePackages = "cn.wecloud.**.mapper")
@SpringBootApplication
public class LayimServer {
    public static void main(String[] args) {
        SpringApplication.run(LayimServer.class,args);
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
