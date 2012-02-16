package com.rackspace.com.papi.components.checker

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.rackspace.com.papi.components.checker.step.Step
import com.rackspace.com.papi.components.checker.step.Result

import com.rackspace.com.papi.components.checker.servlet._

class ValidatorException(msg : String, cause : Throwable) extends Throwable(msg, cause) {}

abstract class Validator {

  val startStep : Step
  val resultHandler : ResultHandler

  def validate (req : HttpServletRequest, res : HttpServletResponse) : Unit = {
    try {
      resultHandler.handle(startStep.check (new CheckerServletRequest (req), new CheckerServletResponse(res), 0).get)
    } catch {
      case v : ValidatorException => throw v
      case e => throw new ValidatorException("Error while validating request", e)
    }
  }
}
