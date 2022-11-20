//package com.example.consumer;
//
//import java.io.PrintWriter;
//
//import org.junit.platform.engine.discovery.DiscoverySelectors;
//import org.junit.platform.launcher.Launcher;
//import org.junit.platform.launcher.LauncherDiscoveryRequest;
//import org.junit.platform.launcher.TestExecutionListener;
//import org.junit.platform.launcher.TestPlan;
//import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
//import org.junit.platform.launcher.core.LauncherFactory;
//import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
//
//
//public class TestLauncher {
//
//  public static void main(String[] args) {
//    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
//      .selectors(DiscoverySelectors.selectClass("com.example.consumer"))
////      .configurationParameter("junit.conditions.deactivate", "com.baeldung.extensions.*")
//      .configurationParameter("junit.jupiter.extensions.autodetection.enabled", "true")
//      .build();
//
//    TestPlan plan = LauncherFactory.create()
//      .discover(request);
//    Launcher launcher = LauncherFactory.create();
//    SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
//    launcher.execute(request, new TestExecutionListener[]{summaryGeneratingListener});
//    launcher.execute(request);
//
//    summaryGeneratingListener.getSummary()
//      .printTo(new PrintWriter(System.out));
//  }
//}
