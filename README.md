# Building Serverless Architectures
This is the code repository for [Building Serverless Architectures](https://www.packtpub.com/application-development/building-serverless-architectures?utm_source=github&utm_medium=repository&utm_content=9781787129191), published by [Packt](https://www.packtpub.com/?utm_source=github). It contains all the supporting project files necessary to work through the book from start to finish.

## About the Book
The focus of this book is to design serverless architectures, and weigh the advantages and disadvantages of this approach, along with decision factors to consider. You will learn how to design a serverless application, get to know that key points of services that serverless applications are based on, and known issues and solutions.
The book addresses key challenges such as how to slice out the core functionality of the software to be distributed in different cloud services and cloud functions. It covers basic and advanced usage of these services, testing and securing the serverless software, automating deployment, and more.
By the end of the book, you will be equipped with knowledge of new tools and techniques to keep up with this evolution in the IT industry.

## Instructions and Navigation
The code for Chapter 05 is organized into one folder, Chapter05 and the rest in one separate folder.

The code will look like the following:

```
cloudFormation {
  capabilityIam true
  templateFile project.file('cloudformation.template')
  templateBucket deploymentBucketName
  templateKeyPrefix "cfn-templates"
  stackName "serverlessbook"
}
```
 
 ## Related Products
* [Building Serverless Web Applications](https://www.packtpub.com/application-development/building-serverless-web-applications?utm_source=github&utm_medium=repository&utm_content=9781787126473)

* [Going Serverless with .NET [Video]](https://www.packtpub.com/application-development/going-serverless-net-video?utm_source=github&utm_medium=repository&utm_content=9781787281295)

* [Serverless computing with Azure and .NET](https://www.packtpub.com/application-development/serverless-computing-azure-and-net?utm_source=github&utm_medium=repository&utm_content=9781787288393)

### Suggestions and Feedback
[Click here](https://docs.google.com/forms/d/e/1FAIpQLSe5qwunkGf6PUvzPirPDtuy1Du5Rlzew23UBp2S-P3wB-GcwQ/viewform) if you have any feedback or suggestions.

