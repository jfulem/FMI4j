package no.mechatronics.sfi.fmi4j.modeldescription.jacksonxml

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import no.mechatronics.sfi.fmi4j.modeldescription.ModelDescriptionImpl
import no.mechatronics.sfi.fmi4j.modeldescription.ModelDescriptionParser
import no.mechatronics.sfi.fmi4j.modeldescription.TEST_FMUs
import org.junit.Assert
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class TestJackson {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(TestJackson::class.java)
    }

    @Test
    fun test() {

        val file = File(TEST_FMUs, "FMI_2.0/CoSimulation/win64/FMUSDK/2.0.4/BouncingBall/bouncingBall.fmu")
        Assert.assertTrue(file.exists())

        val mapper = XmlMapper().apply {
            registerModule(KotlinModule())
            registerModule(JacksonXmlModule())
            enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        }
        val md = mapper.readValue<ModelDescriptionImpl>(ModelDescriptionParser.extractModelDescriptionXml(file))


        LOG.info("${md.modelVariables.size}")
        LOG.info("${md.modelStructure.outputs}")
        LOG.info("${md.modelStructure.derivatives}")

    }

    class Real

    class ScalarVariable {

        @JsonSetter(nulls = Nulls.AS_EMPTY)
        @JacksonXmlProperty(localName = "Real")
        var real: Real? = null

    }

    @Test
    fun test2() {

        val xml = """
            <ScalarVariable >
                <Real />
             </ScalarVariable >
            """

        val mapper = XmlMapper().apply {
            registerModule(KotlinModule())
        }
        val variable = mapper.readValue<ScalarVariable>(xml)

        Assert.assertNotNull(variable.real)

    }

}