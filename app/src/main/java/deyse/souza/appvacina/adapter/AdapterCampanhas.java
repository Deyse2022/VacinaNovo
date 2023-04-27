package deyse.souza.appvacina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.model.Campanha;

public class AdapterCampanhas extends RecyclerView.Adapter <AdapterCampanhas.MyViewHolder>{

    private List<Campanha> campanhas;
    private Context context;

    public AdapterCampanhas(List<Campanha> campanhas, Context context){
        this.campanhas = campanhas;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeCampanha;
        TextView status;
        TextView dtinicio;
        TextView dtfim;
        TextView infoad;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeCampanha = itemView.findViewById(R.id.textNomeCampanha);
            status = itemView.findViewById(R.id.textStatus);
            dtinicio = itemView.findViewById(R.id.textDtinicio);
            dtfim = itemView.findViewById(R.id.textDtfim);
            infoad = itemView.findViewById(R.id.textInfoad);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_campanhas,parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCampanhas.MyViewHolder holder, int position) {
        Campanha campanha = campanhas.get(position);
        holder.nomeCampanha.setText(campanha.getNome());
        holder.status.setText(campanha.getStatus());
        holder.dtinicio.setText(campanha.getDtinicio());
        holder.dtfim.setText(campanha.getDtfim());
        holder.infoad.setText(campanha.getDadosad());
    }

    @Override
    public int getItemCount() {
        return campanhas.size();
    }

}
