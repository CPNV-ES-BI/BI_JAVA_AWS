package groovy

import groovy.xml.XmlSlurper

def reportFile = new File("target/site/jacoco/index.html")

if (!reportFile.exists() || !reportFile.canRead()) {
    logInfo "JaCoCo Printer - Skipped due to missing report file."
    return
}

reportFile.withReader('UTF-8') { reader ->
    def html = getParser().parseText(reader.readLine())
    def lines = html.body.table.tbody.tr.findAll { it.td.a.@class == 'el_package' }

    lines.each { line ->
        def packageName = line.td.a.text()
        def instructionRatio = line.td[2]
        def branchRatio = line.td[4]

        def instructionMessage = "${instructionRatio} of instructions covered"
        def branchMessage = "${branchRatio} of branches covered"

        logInfo "JaCoCo Printer - ${packageName} : ${instructionMessage}, ${branchMessage}."
    }

    def totalRow = html.body.table.tfoot.tr
    def totalInstructionRatio = totalRow.td[2]
    def totalBranchRatio = totalRow.td[4]

    def totalInstructionMessage = "${totalInstructionRatio} of instructions covered"
    def totalBranchMessage = "${totalBranchRatio} of branches covered"

    logInfo "JaCoCo Printer - Total : ${totalInstructionMessage}, ${totalBranchMessage}."
}

XmlSlurper getParser() {
    parser = new XmlSlurper()
    parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
    parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    return parser
}

void logInfo(String message) {
    def style = "${(27 as char)}[34m"
    def infoPrefix = "[${style}INFO${(27 as char)}[0m]"
    println "${infoPrefix} ${message}"
}
