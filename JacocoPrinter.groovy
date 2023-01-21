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
        logInfo "JaCoCo Printer - ${packageName} : " + instructionMessage + ", ${branchRatio} of branches covered."
    }

    def totalRow = html.body.table.tfoot.tr
    def totalInstructionRatio = totalRow.td[2]
    def totalBranchRatio = totalRow.td[4]

    logInfo "JaCoCo Printer - ${totalInstructionRatio} of total instructions covered"
    logInfo "JaCoCo Printer - ${totalBranchRatio} of total branches covered"
}

XmlSlurper getParser() {
    parser = new XmlSlurper()
    parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
    parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    return parser
}

void logInfo(String message) {
    def style = "${(char) 27}[34m"
    def infoPrefix = "[${style}INFO${(char) 27}[0m]"
    println "${infoPrefix} ${message}"
}
