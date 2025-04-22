package com.project.java.test.codetest;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.project.java.test.codetest.model.CodeTestDAO;

/**
 * 에빙하우스의 망각곡선 알고리즘을 적용하여 사용자의 기억력 점수를 주기적으로 감소시키는 스케줄러 서블릿입니다.
 * 애플리케이션 시작 시 자동으로 로드되며, 24시간마다 기억력 감소 작업을 실행합니다.
 * 이 스케줄러는 웹 애플리케이션이 실행되는 동안 백그라운드에서 지속적으로 실행됩니다.
 *
 * <p>에빙하우스의 망각곡선 개념:</p>
 * <ul>
 *   <li>학습한 내용은 시간이 지남에 따라 기억 보존률이 지수함수적으로 감소</li>
 *   <li>주기적인 복습을 통해 기억 유지 기간을 연장할 수 있음</li>
 *   <li>이 스케줄러는 사용자가 복습하지 않은 내용에 대한 기억력 점수를 감소시켜 복습이 필요한 주제를 식별</li>
 * </ul>
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>웹 애플리케이션 시작 시 자동 실행</li>
 *   <li>24시간마다 주기적으로 기억력 점수 감소 작업 실행</li>
 *   <li>데몬 스레드를 사용하여 메인 애플리케이션 실행에 영향 최소화</li>
 * </ul>
 *
 * @author [박주승]
 * @version 1.0
 */
@WebServlet(name = "MemoryDecayScheduler", loadOnStartup = 1)
public class MemoryDecayScheduler extends HttpServlet {
    /** 주기적인 작업 실행을 위한 타이머 */
    private Timer timer;

    /**
     * 서블릿 초기화 메서드입니다.
     * 웹 애플리케이션 시작 시 자동으로 호출되어 타이머를 설정하고 기억력 감소 작업을 스케줄링합니다.
     *
     * <p>타이머 설정:</p>
     * <ul>
     *   <li>데몬 스레드로 실행되어 메인 애플리케이션 종료 시 자동 종료</li>
     *   <li>첫 실행은 즉시(0ms 지연), 이후 24시간(24 * 60 * 60 * 1000ms)마다 실행</li>
     *   <li>각 실행 시 CodeTestDAO의 decayMemoryScores() 메서드 호출</li>
     * </ul>
     *
     * @throws ServletException 서블릿 초기화 중 오류 발생 시
     */
    @Override
    public void init() throws ServletException {
        // 데몬 스레드로 타이머 생성 (애플리케이션 종료 시 자동 종료)
        timer = new Timer(true);

        // 실제 적용 (24시간마다 실행)
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                CodeTestDAO dao = new CodeTestDAO();
                dao.decayMemoryScores(); // 기억력 감소 실행
            }
        }, 0, 24 * 60 * 60 * 1000);

        // 스케줄러 시작 로그 출력
        System.out.println(" Memory decay scheduler started!");
    }

    /**
     * 서블릿 종료 메서드입니다.
     * 웹 애플리케이션 종료 시 호출되어 실행 중인 타이머를 정상적으로 종료합니다.
     * 이는 자원 누수를 방지하고 애플리케이션의 정상적인 종료를 보장합니다.
     */
    @Override
    public void destroy() {
        timer.cancel();
    }
}