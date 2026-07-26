mod data_processor {
    include!("../gen/data_processor/v1/data_processor.v1.rs");
}

use tonic::{Request, Response, Status, transport::Server};

use data_processor::{
    GiveDataInsightsRequest, GiveDataInsightsResponse,
    data_processor_service_server::{DataProcessorService, DataProcessorServiceServer},
};

#[derive(Debug, Default)]
pub struct WorkerNode {}

#[tonic::async_trait]
impl DataProcessorService for WorkerNode {}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let add = "127.0.0.1:15001".parse()?;
    let service = WorkerNode::default();

    Server::builder()
        .add_service(DataProcessorServiceServer::new(service))
        .serve(add)
        .await?;

    Ok(())
}
