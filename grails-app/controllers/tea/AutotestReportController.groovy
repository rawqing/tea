package tea

import constant.Common
import constant.Conf
import grails.converters.JSON
import groovy.json.JsonSlurper
import org.apache.commons.io.filefilter.DirectoryFileFilter
import utils.FileRW

import java.text.DecimalFormat
import java.util.regex.Pattern

class AutotestReportController {
    FileRW fr = new FileRW()
    DecimalFormat df = new DecimalFormat("#.00")
    def currentJson = null
    def currentJsonName = ""

    def index() { }

    def showClass(){
        String dirPath = request.getSession().getServletContext().getRealPath("/") + Common.android_report_dir
        String fileName = params.fileName
        String className = params.className
        println fileName + " "+ className

        def json = getJson(dirPath , fileName)
        def classes = json.suiteBean.childSuites.testClasses
        def clz = getTestClass(classes, className)
        def data = []

        return [deviceDesc:json.deviceDesc ,reportName:json.reportName ,data: data]
    }
    def showDetails(){
        String dirPath = request.getSession().getServletContext().getRealPath("/") + Common.android_report_dir
        String name = params.fileName
        def json = getJson(dirPath , name)
        def data = []
        def suites = json.suiteBean.childSuites
        for (def suite : suites) {
            def suiteName = suite.suiteName
            def classes = []
            def total = [passed: 0, skipped: 0,
                         uncalibrated: 0, failed: 0 ,passRate:0]
            for (def clz : suite.testClasses) {
                def cName = clz.testClassName
                def duration = clz.spendTime / 1000
                def statusCount = clz.status_count
                int passed = Integer.valueOf(statusCount.SUCCEEDED)

                int skipped = Integer.valueOf(statusCount.SKIPPED)
                int uncalibrated = Integer.valueOf(statusCount.UNCALIBRATED ?: 0)
                int failed = Integer.valueOf(statusCount.FAILED)
                int count = (passed + skipped + uncalibrated + failed)?: 1

                def passRate = df.format(passed / count * 100)+"%"
                classes.add([className: cName, duration: duration+"s", passed: passed, skipped: skipped,
                             uncalibrated: uncalibrated, failed: failed, passRate: passRate])
                total.passed += passed
                total.skipped += skipped
                total.uncalibrated += uncalibrated
                total.failed += failed
            }
            int tcont = (total.passed + total.skipped + total.uncalibrated + total.failed) ?: 1
            total.passRate = df.format(total.passed / tcont * 100) +"%"

            data.add([suiteName:suiteName ,classes:classes ,total:total])
        }
        return [fileName: name, deviceDesc:json.deviceDesc ,reportName:json.reportName ,data: data]
    }
    def android() {
        String dirPath = request.getSession().getServletContext().getRealPath("/") + File.separator + Common.android_report_dir
        File dir = new File(dirPath)
        fr.createDir(dir)

        List<File> fl = dir.listFiles(new FilenameFilter(){
            @Override
            boolean accept(File file, String name) {
                Pattern pattern = Pattern.compile(Conf.report_initially + ".*\\.json");

                return pattern.matcher(name).matches();
            }
        })
        def data = []
        for (File file : fl) {
            String fm = file.getName()
            def jsonLoad = "{}"
            jsonLoad = file.text
            def json = new JsonSlurper().parseText(jsonLoad)
            data.add([strDate:spitFileName(fm) ,fileName:fm ,json: json])
        }
        return [data: data]
    }
    def upload() {
        String dirPath = request.getSession().getServletContext().getRealPath("/") + Common.android_report_dir


        File file = new File(dirPath + "rm.json")
        if (file.exists()) {
            render(file.getAbsolutePath())
        }
        render(bad)
    }
    def ios(){}

    def web(){}

    private spitFileName(String name) {
        def f = name.split('\\.')
        String fname = f[0]
        def sl = fname.split("_")
        long time = Long.parseLong(sl[sl.length-1])
        Date date = new Date(time)
        def sd = date.format("yyyy-MM-dd hh:mm:ss")
        return sd
    }

    private getJson(String path ,String name) {
        if (name.equals(currentJsonName)) {
            return this.currentJson
        }else{
            return loadJson(path ,name)
        }
    }
    private loadJson(String path ,String name) {
        println "load json : ${path+name}"
        this.currentJson = null
        this.currentJsonName = ""
        File file = new File(path + name)
        if (!file.exists()) {
            render path +" not exists"
            return null
        }
        def jsonLoad = "{}"
        jsonLoad = file.text

        def json = new JsonSlurper().parseText(jsonLoad)
        this.currentJson = json
        this.currentJsonName = name
        return json
    }

    private getTestClass(classes ,String className) {
        for (def clz : classes) {
            if(className.equals(clz.testClassName)){
                return clz
            }
        }
        return null
    }
}
