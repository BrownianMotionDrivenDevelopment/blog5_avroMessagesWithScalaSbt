package com.brownianmotiondrivendev.avromessages

import java.io.File

import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.io.{DatumReader, DatumWriter}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import org.scalatest.FunSuite

class GreetingTest extends FunSuite {


  test("Basic, literal 'Happy Path' Greeting test") {
    val happyGreeting = Greeting(greetingEmotion = GreetingEmotion.JOY, greetingDetail = "Top of the morning to you!")

    println(s"I have a feeling of ${happyGreeting.greetingEmotion.toString} so I say '${happyGreeting.greetingDetail}'")
    assert(happyGreeting.greetingEmotion == GreetingEmotion.JOY)
  }


  test("Test using file (de)serialization") {
    val sadGreeting = Greeting(greetingEmotion = GreetingEmotion.SADNESS, greetingDetail = "Boo hoo! Hello.")

    val GreetingFileName = "greetingTest.avro"
    val GreetingAvroFile = new File(GreetingFileName)
    GreetingAvroFile.delete()

    // serialize
    val speculativeScreenplayDatumWriter: DatumWriter[Greeting] = new SpecificDatumWriter[Greeting](Greeting.SCHEMA$)
    val dataFileWriter: DataFileWriter[Greeting] = new DataFileWriter[Greeting](speculativeScreenplayDatumWriter)
    dataFileWriter.create(Greeting.SCHEMA$, GreetingAvroFile)
    dataFileWriter.append(sadGreeting)
    dataFileWriter.close()

    // Deserialize from disk
    val speculativeScreenplayDatumReader: DatumReader[Greeting] = new SpecificDatumReader[Greeting](Greeting.SCHEMA$)
    val dataFileReader: DataFileReader[Greeting] =
      new DataFileReader[Greeting](new File(GreetingFileName), speculativeScreenplayDatumReader)

    var deserialisedGreeting: Greeting = null // a var ... OMG, ahhhhhhh - avert your eyes!!!!
    while (dataFileReader.hasNext) {
      deserialisedGreeting = dataFileReader.next(deserialisedGreeting)
      println("okay: " + deserialisedGreeting + "; dokies")
    }

    GreetingAvroFile.delete()

    assert(sadGreeting.greetingEmotion == GreetingEmotion.SADNESS)
  }

}

