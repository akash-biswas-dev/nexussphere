mod data_processor {
    include!("gen/data_processor/v1/data_processor.v1.rs");
}

use tonic::{Request, Response, Status, transport::Server};

use data_processor::{
    GiveDataInsightsRequest, GiveDataInsightsResponse,
    data_processor_service_server::{DataProcessorService, DataProcessorServiceServer},
};

#[derive(Debug, Default)]
pub struct WorkerNode {}

#[tonic::async_trait]
impl DataProcessorService for WorkerNode {
    async fn give_data_insights(
        &self,
        req: Request<GiveDataInsightsRequest>,
    ) -> std::result::Result<Response<GiveDataInsightsResponse>, Status> {
        let data_info = req.into_inner();
        println!("{}", data_info.data_source_id);
        Ok(Response::new(GiveDataInsightsResponse {
            resp_message: String::from("Hello"),
        }))
    }
}

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
