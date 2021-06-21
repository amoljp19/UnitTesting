package com.softaai.unittesting.data.remote

import com.softaai.unittesting.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith("marketing") -> getMockJson
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        "application/json".toMediaTypeOrNull(),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }
}

const val getMockJson = "[\n" +
        "  {\n" +
        "    \"id\": \"7638eee4-4e75-4c06-a920-ea7619a311b5\",\n" +
        "    \"type\": \"Full Time\",\n" +
        "    \"url\": \"https://jobs.github.com/positions/7638eee4-4e75-4c06-a920-ea7619a311b5\",\n" +
        "    \"created_at\": \"Tue May 18 17:12:52 UTC 2021\",\n" +
        "    \"company\": \"MANDARIN MEDIEN Gesellschaft für digitale Lösungen mbH\",\n" +
        "    \"company_url\": \"https://www.mandarin-medien.de/\",\n" +
        "    \"location\": \"Schwerin\",\n" +
        "    \"title\": \"Systemadministrator:in\",\n" +
        "    \"description\": \"\\u003cp\\u003e2002 sind wir als Internetagentur gestartet. Heute bezeichnen wir uns als Digitalagentur. Das Spielfeld ist heute breiter und greift tiefer in bestehende Geschäftsbereiche ein. In unseren 3 Units MARKETING, DIGITAL \\u0026amp; TALENT arbeiten über 80 Macher, Nerds und Kreative nach einem Prinzip: Messbar mehr Erfolg. Das heißt konkret: Klare Ziele. Permanente Optimierung. Transparentes Reporting.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eWer wir sind\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003eWir sind Zuhörer, Strategen, Kreative, Experten, Kollegen, Freunde, Spaßmacher, keine Großeredenschwinger, Stille, Laute, Sprachgewandte, Texter, Erfolgshungrige, Macher.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eWIR HELFEN MENSCHEN UND MARKEN ÜBER SICH HINAUSZUWACHSEN.\\nWIR SIND MANDARIN MEDIEN!\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas wären Deine Aufgaben\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003eWir bieten Dir einen freien Platz in unserem IT Team als Systemadministrator:in in unserer Digitalagentur in Schwerin. Mit deinem Team wirst du für unsere Kunden den digitalen Ausbau vorantreiben und die Chance bekommen unsere digitale Unit noch weiter auszubauen und größer werden zu lassen.\\nChristian, Christoph, Fabrice, Gordon, Gérard, Marcel, Christoph, Moritz, Sebastian \\u0026amp; Steffen freuen sich, wenn DU ihr Team komplett machst.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas wären Deine Aufgaben\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eErster Ansprechpartner für deine Kunden, via Telefon, E-Mail, Remote oder auch direkt vor Ort\\u003c/li\\u003e\\n\\u003cli\\u003eUmgang mit verschiedenen Themen und Umgebungen\\nz.B: Client-, Server-, Netzwerk- und im Cloudbereich\\u003c/li\\u003e\\n\\u003cli\\u003eFehleranalyse und -beseitigung\\u003c/li\\u003e\\n\\u003cli\\u003eInstallation, Administration und Wartung von Kundeninfrastruktur\\u003c/li\\u003e\\n\\u003cli\\u003eFirst- und Second-Level Support\\u003c/li\\u003e\\n\\u003cli\\u003eProjektbezogene Planung, Vorbereitung und Durchführung\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas bringst Du mit\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eEine abgeschlossene Ausbildung als Fachinformatiker für Systemintegration\\u003c/li\\u003e\\n\\u003cli\\u003eFührerschein Klasse B\\u003c/li\\u003e\\n\\u003cli\\u003eKunden- sowie Lösungsorientiertes handeln\\u003c/li\\u003e\\n\\u003cli\\u003eVerantwortungsbewusstsein für deine eigenständige Zeiteinteilung\\u003c/li\\u003e\\n\\u003cli\\u003eBereitschaft jeden Tag dazu zu lernen und mit deinen Aufgaben zu wachsen\\u003c/li\\u003e\\n\\u003cli\\u003eSpaß an der Arbeit im Team und mit dem Team neue Ideen zu entwickeln\\u003c/li\\u003e\\n\\u003cli\\u003eZertifizierungen im Bereich Microsoft, VMWare und Cisco wünschenswert\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003eKlingt ganz schön viel? Nobody is perfect! Wir lernen alle jeden Tag voneinander und nehmen uns jede Woche Zeit für Weiterbildung. Die ein oder andere Fähigkeit kannst Du also auch nach Deinem Start bei uns erlernen oder vertiefen.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas bieten wir Dir\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eEinen spannenden und anspruchsvollen Agentur-Job in MV\\u003c/li\\u003e\\n\\u003cli\\u003eEine große Bandbreite an Projekten - Handwerk bis Konzern\\u003c/li\\u003e\\n\\u003cli\\u003eEin hervorragendes Arbeitsklima und kurze Entscheidungswege\\u003c/li\\u003e\\n\\u003cli\\u003eZeit für Deine Weiterbildung,  je Woche, plus Budget\\u003c/li\\u003e\\n\\u003cli\\u003eModerne Arbeitsmittel und neueste Programme\\u003c/li\\u003e\\n\\u003cli\\u003eUnterstützung Deiner Gesundheit\\u003c/li\\u003e\\n\\u003cli\\u003eMöglichkeit eines Fahrtkostenzuschusses\\u003c/li\\u003e\\n\\u003cli\\u003eFlexibles Arbeiten - von Zuhause oder einfach mal am Strand\\u003c/li\\u003e\\n\\u003cli\\u003eKreative Denkpausen mit dem Boot auf dem Schweriner See\\u003c/li\\u003e\\n\\u003cli\\u003eTeamevents, wie z.B. Grillen, MANDARIN-Kino, Besuch des Weihnachtsmarktes uvm.\\u003c/li\\u003e\\n\\u003cli\\u003eEin Büro mitten in der City sowie leckeres Obst, Kaffee, Tee, Wasser und Mate\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eKontakt\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003e\u200B\u200B\u200B\u200B\u200BPasst alles für Dich?\\u003c/p\\u003e\\n\\u003cp\\u003eDann sende Deine Bewerbung mit Deinen Vorstellungen zu Gehalt und Urlaub an Corinna Lepsien, \\u003ca href=\\\"mailto:jobs@mandarin-medien.de\\\"\\u003ejobs@mandarin-medien.de\\u003c/a\\u003e. Falls Du gerade unterwegs bist, kannst Du uns auch einfach einen Link von Deinem Xing- oder LinkedIn Profil schicken.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cimg src=\\\"https://camo.githubusercontent.com/26c2737b75fe2e3161d0a357a200081f9e8d0243/68747470733a2f2f742e676f686972696e672e636f6d2f682f31306564346231663739316361656138313233643961343663393034333134353938646566643238663733363838366634363661363332383333376361376130\\\"\\u003e\\u003c/p\\u003e\\n\",\n" +
        "    \"how_to_apply\": \"\\u003cp\\u003e\\u003ca href=\\\"https://t.gohiring.com/h/83f7df34249addb0bebc8dc680310d1ded43220a748ee285e27989a457dd10ea\\\"\\u003eApplication form\\u003c/a\\u003e\\u003c/p\\u003e\\n\",\n" +
        "    \"company_logo\": \"https://jobs.github.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBaCtqIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--8d0339b9e43b0dca160b9db73b230b6067e39b05/MANDARIN%20MEDIEN%20Gesellschaft%20fu%CC%88r%20digitale%20Lo%CC%88sungen%20mbH.jpeg\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"1f004d5a-ef14-4533-8c0b-5f354978ddae\",\n" +
        "    \"type\": \"Full Time\",\n" +
        "    \"url\": \"https://jobs.github.com/positions/1f004d5a-ef14-4533-8c0b-5f354978ddae\",\n" +
        "    \"created_at\": \"Tue May 18 17:09:49 UTC 2021\",\n" +
        "    \"company\": \"MANDARIN MEDIEN Gesellschaft für digitale Lösungen mbH\",\n" +
        "    \"company_url\": \"https://www.mandarin-medien.de/\",\n" +
        "    \"location\": \"Schwerin\",\n" +
        "    \"title\": \"IT-System- und Netzwerkadministrator:in\",\n" +
        "    \"description\": \"\\u003cp\\u003e2002 sind wir als Internetagentur gestartet. Heute bezeichnen wir uns als Digitalagentur. Das Spielfeld ist heute breiter und greift tiefer in bestehende Geschäftsbereiche ein. In unseren 3 Units MARKETING, DIGITAL \\u0026amp; TALENT arbeiten über 80 Macher, Nerds und Kreative nach einem Prinzip: Messbar mehr Erfolg. Das heißt konkret: Klare Ziele. Permanente Optimierung. Transparentes Reporting.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eWer wir sind\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003eWir sind Zuhörer, Strategen, Kreative, Experten, Kollegen, Freunde, Spaßmacher, keine Großeredenschwinger, Stille, Laute, Sprachgewandte, Texter, Erfolgshungrige, Macher.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eWIR HELFEN MENSCHEN UND MARKEN ÜBER SICH HINAUSZUWACHSEN.\\nWIR SIND MANDARIN MEDIEN!\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas wären Deine Aufgaben\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003eWir bieten Dir einen freien Platz in unserem IT-Team als Systemadministrator:in, in Schwerin. Mit deinem Team wirst du für unsere Kunden moderne IT Landschaften erschaffen, eigenverantwortlich aber immer mit Teamsupport arbeiten, dich persönlich und fachlich immer weiterentwickeln.\\u003c/p\\u003e\\n\\u003cp\\u003eUnsere IT-Crew entwickelt und supportet zum Beispiel Netzwerke, Cloud Lösungen, TK Anlagen, Live Streamings von Konferenzen, spezifische Branchensoftware und individuelle Digitalisierungsprojekte für Kunden aus den Branchen: Energieversorgung, Sozial- und Wohlfahrtsverbände, Pflegeeinrichtungen sowie Unternehmen aus den Bereichen Lebensmittelindustrie und Medizintechnik.\\u003c/p\\u003e\\n\\u003cp\\u003eChristian, Steffen, Christoph, Fabrice, Gérard, Marcel, Christoph, Moritz, Sebastian \\u0026amp;  Gordon freuen sich, wenn DU ihr Team komplett machst.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas wären Deine Aufgaben\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eErster Ansprechpartner für deine Kunden, via Telefon, E-Mail, Remote oder auch direkt vor Ort\\u003c/li\\u003e\\n\\u003cli\\u003eUmgang mit verschiedenen Themen und Umgebungen\\nz.B: Client-, Server-, Netzwerk- und im Cloudbereich\\u003c/li\\u003e\\n\\u003cli\\u003eFehleranalyse und -beseitigung\\u003c/li\\u003e\\n\\u003cli\\u003eInstallation, Administration und Wartung von Kundeninfrastruktur\\u003c/li\\u003e\\n\\u003cli\\u003eFirst- und Second-Level Support\\u003c/li\\u003e\\n\\u003cli\\u003eProjektbezogene Planung, Vorbereitung und Durchführung\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas bringst Du mit\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eEine abgeschlossene Ausbildung als Fachinformatiker für Systemintegration\\u003c/li\\u003e\\n\\u003cli\\u003eFührerschein Klasse B\\u003c/li\\u003e\\n\\u003cli\\u003eKunden- sowie Lösungsorientiertes handeln\\u003c/li\\u003e\\n\\u003cli\\u003eVerantwortungsbewusstsein für deine eigenständige Zeiteinteilung\\u003c/li\\u003e\\n\\u003cli\\u003eBereitschaft jeden Tag dazu zu lernen und mit deinen Aufgaben zu wachsen\\u003c/li\\u003e\\n\\u003cli\\u003eSpaß an der Arbeit im Team und mit dem Team neue Ideen zu entwickeln\\u003c/li\\u003e\\n\\u003cli\\u003eZertifizierungen im Bereich Microsoft, VMWare und Cisco wünschenswert\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003eKlingt ganz schön viel? Nobody is perfect! Wir lernen alle jeden Tag voneinander und nehmen uns jede Woche Zeit für Weiterbildung. Die ein oder andere Fähigkeit kannst Du also auch nach Deinem Start bei uns erlernen oder vertiefen.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas bieten wir Dir\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eEinen spannenden und anspruchsvollen Agentur-Job in MV\\u003c/li\\u003e\\n\\u003cli\\u003eEine große Bandbreite an Projekten - Handwerk bis Konzern\\u003c/li\\u003e\\n\\u003cli\\u003eEin hervorragendes Arbeitsklima und kurze Entscheidungswege\\u003c/li\\u003e\\n\\u003cli\\u003eZeit für Deine Weiterbildung,  je Woche, plus Budget\\u003c/li\\u003e\\n\\u003cli\\u003eModerne Arbeitsmittel und neueste Programme\\u003c/li\\u003e\\n\\u003cli\\u003eUnterstützung Deiner Gesundheit\\u003c/li\\u003e\\n\\u003cli\\u003eMöglichkeit eines Fahrtkostenzuschusses\\u003c/li\\u003e\\n\\u003cli\\u003eFlexibles Arbeiten - von Zuhause oder einfach mal am Strand\\u003c/li\\u003e\\n\\u003cli\\u003eKreative Denkpausen mit dem Boot auf dem Schweriner See\\u003c/li\\u003e\\n\\u003cli\\u003eTeamevents, wie z.B. Grillen, MANDARIN-Kino, Besuch des Weihnachtsmarktes uvm.\\u003c/li\\u003e\\n\\u003cli\\u003eEin Büro mitten in der City sowie leckeres Obst, Kaffee, Tee, Wasser und Mate\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eKontakt\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003ePasst alles für Dich?\\u003c/p\\u003e\\n\\u003cp\\u003eDann sende Deine Bewerbung mit Deinen Vorstellungen zu Gehalt und Urlaub an Corinna Lepsien, \\u003ca href=\\\"mailto:jobs@mandarin-medien.de\\\"\\u003ejobs@mandarin-medien.de\\u003c/a\\u003e. Falls Du gerade unterwegs bist, kannst Du uns auch einfach einen Link von Deinem Xing- oder LinkedIn Profil schicken.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cimg src=\\\"https://camo.githubusercontent.com/7c8050f30305b6c7e0a255c3a35090adff7c8b0c/68747470733a2f2f742e676f686972696e672e636f6d2f682f62303261636662623138313036343332356430333531393536326135323633336262376136336436326166313763306638313463323663623362646166303565\\\"\\u003e\\u003c/p\\u003e\\n\",\n" +
        "    \"how_to_apply\": \"\\u003cp\\u003e\\u003ca href=\\\"https://t.gohiring.com/h/5f4466dcc594ef01a23adea56b03b1804a0ed6fae16d790b9758d8192250fb0c\\\"\\u003eApplication form\\u003c/a\\u003e\\u003c/p\\u003e\\n\",\n" +
        "    \"company_logo\": \"https://jobs.github.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBaDJqIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--8ad2112c9ebe7a1e14f3dee7b448a1c1a37abe03/MANDARIN%20MEDIEN%20Gesellschaft%20fu%CC%88r%20digitale%20Lo%CC%88sungen%20mbH.jpeg\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": \"8913f893-e6e3-4d50-aaf4-94f8eb479ead\",\n" +
        "    \"type\": \"Full Time\",\n" +
        "    \"url\": \"https://jobs.github.com/positions/8913f893-e6e3-4d50-aaf4-94f8eb479ead\",\n" +
        "    \"created_at\": \"Tue May 18 17:06:20 UTC 2021\",\n" +
        "    \"company\": \"MANDARIN MEDIEN Gesellschaft für digitale Lösungen mbH\",\n" +
        "    \"company_url\": \"https://www.mandarin-medien.de/\",\n" +
        "    \"location\": \"Schwerin\",\n" +
        "    \"title\": \"Frontend Entwickler:in\",\n" +
        "    \"description\": \"\\u003cp\\u003e2002 sind wir als Internetagentur gestartet. Heute bezeichnen wir uns als Digitalagentur. Das Spielfeld ist heute breiter und greift tiefer in bestehende Geschäftsbereiche ein. In unseren 3 Units MARKETING, DIGITAL \\u0026amp; TALENT arbeiten über 80 Macher, Nerds und Kreative nach einem Prinzip: Messbar mehr Erfolg. Das heißt konkret: Klare Ziele. Permanente Optimierung. Transparentes Reporting.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eWer wir sind\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003e2002 sind wir als Agentur gestartet. Heute arbeiten über 80 Macher, Nerds und Kreative bei uns. Wir sind Strategen, Experten, Zuhörer, Kollegen, Freunde, Aufdenpunktbringer, Sprachgewandte, Texter und Erfolgshungrige.\\nGemeinsam entwickeln wir sehr wirkungsvolle Lösungen in den Bereichen Digitales Marketing, Employer Branding und Digitalisierung für Marken, wie Cinestar, Vodafone, Garmin, Wemag und viele mehr. Wir entwickeln Online-Marketing-Strategien, bauen innovative Websites und haben auch sonst die gesamte Vielfalt des Agenturlebens zu bieten.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas wären Deine Aufgaben\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDu willst eine Mandarine werden?\\u003c/strong\\u003e\\nDu liebst gut bedienbare, gut aussehende und gut funktionierende Websites?\\nDu liebst es noch mehr, diese selbst zu entwickeln?\\nDu möchtest mit einem erfahrenen Team aus Anwendungsentwicklern, Konzeptern, Designern, Marketingexperten uvm. arbeiten? Eine Crew hinter dir haben, dir dir das Frontend überlässt?\\nDann bist du die perfekte Mandarine im Bereich FRONTEND DEVELOPMENT!\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas bringst Du mit\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDAS SOLLTEST DU MITBRINGEN:\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eFundierte Kenntnisse in HTML,CSS, Javascript\\u003c/li\\u003e\\n\\u003cli\\u003eUmgang mit entsprechenden Techniken und Frameworks (React, LESS/SASS, Tailwind, Bootstrap o.ä.)\\u003c/li\\u003e\\n\\u003cli\\u003eEigenständiges Arbeiten, Teamgeist, Kommunikationsfähigkeit\\u003c/li\\u003e\\n\\u003cli\\u003eEin sehr hohes Qualitätsbewusstsein\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eVon Vorteil sind:\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eGrundlegende Programmierkenntnisse z.B. in PHP\\u003c/li\\u003e\\n\\u003cli\\u003eVerständnis von OOP\\u003c/li\\u003e\\n\\u003cli\\u003eGIT Kenntnisse\\u003c/li\\u003e\\n\\u003cli\\u003eVerständnis von Datenbanken, z.B. MySQL\\u003c/li\\u003e\\n\\u003cli\\u003eVerständnis von API\\u003c/li\\u003e\\n\\u003cli\\u003eAlltagssicheres Englisch\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eNahezu großartig wären:\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eDrupal 7\\u0026amp;8 Kenntnisse\\u003c/li\\u003e\\n\\u003cli\\u003eWordPress Erfahrung\\u003c/li\\u003e\\n\\u003cli\\u003eVerständnis von Template Engines wie Twig oder Blade\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003eKlingt ganz schön viel? Nobody is perfect! Wir lernen jeden Tag voneinander und nehmen uns jede Woche Zeit für Weiterbildung. Die ein oder andere Fähigkeit kannst Du also auch nach Deinem Start bei uns erlernen oder vertiefen.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDas bieten wir Dir\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eDAS KÖNNEN WIR DIR BIETEN:\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eEinen spannenden und anspruchsvollen Agentur-Job in MV\\u003c/li\\u003e\\n\\u003cli\\u003eEine große Bandbreite an Projekten - Handwerk bis Konzern\\u003c/li\\u003e\\n\\u003cli\\u003eEin hervorragendes Arbeitsklima und kurze Entscheidungswege\\u003c/li\\u003e\\n\\u003cli\\u003e4 Stunden Zeit für deine Weiterbildung je Woche plus Budget\\u003c/li\\u003e\\n\\u003cli\\u003eNeueste Arbeitsmittel und Programme\\u003c/li\\u003e\\n\\u003cli\\u003eUnterstützung deiner Gesundheit\\u003c/li\\u003e\\n\\u003cli\\u003eFahrtkostenzuschuss\\u003c/li\\u003e\\n\\u003cli\\u003eFlexible Arbeitsorte\\u003c/li\\u003e\\n\\u003cli\\u003eKreative Denkpausen mit dem Boot auf dem Schweriner See\\u003c/li\\u003e\\n\\u003cli\\u003eTeamevents, wie z.B. Grillen, MANDARIN-Kino, Besuch des Weihnachtsmarktes und noch ein paar mehr\\u003c/li\\u003e\\n\\u003cli\\u003eEin Büro mit Blick auf den Schweriner See sowie lecker Obst, Kaffee, Tee, Wasser und Mate\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003e\\u003cstrong\\u003eKontakt\\u003c/strong\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003ePasst alles für Dich? Dann schick deine Bewerbung mit deinen Vorstellungen zu Gehalt und Urlaub an Corinna Lepsien, \\u003ca href=\\\"mailto:jobs@mandarin-medien.de\\\"\\u003ejobs@mandarin-medien.de\\u003c/a\\u003e. Oder falls du gerade unterwegs bist einfach einen Link zu deinem Xing- oder LinkedIn Profil.\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003cimg src=\\\"https://camo.githubusercontent.com/da6ee29a15ea655c323d5f0c1b730031e993f7a6/68747470733a2f2f742e676f686972696e672e636f6d2f682f37306233363363363563343163653431666337333065333861633565626333313838333133653163613361323831303233333738376431333236313139303537\\\"\\u003e\\u003c/p\\u003e\\n\",\n" +
        "    \"how_to_apply\": \"\\u003cp\\u003e\\u003ca href=\\\"https://t.gohiring.com/h/eaf3f0c97d54fa8a2cc87ea55ba8e9a4ab96c37d6c2f915a3e302cb450ce3602\\\"\\u003eApplication form\\u003c/a\\u003e\\u003c/p\\u003e\\n\",\n" +
        "    \"company_logo\": \"https://jobs.github.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBaHVqIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--0814678c4cc00f78262bd84da10eed4e7d39f77c/MANDARIN%20MEDIEN%20Gesellschaft%20fu%CC%88r%20digitale%20Lo%CC%88sungen%20mbH.jpeg\"\n" +
        "  }\n" +
        "]"